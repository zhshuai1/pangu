package com.sepism.pangu.processor;

import com.sepism.pangu.model.DbRedisConverter;
import com.sepism.pangu.model.answer.QuestionAnswer;
import com.sepism.pangu.model.questionnaire.Question;
import com.sepism.pangu.model.repository.QuestionnaireReportRepositoryRedis;
import com.sepism.pangu.util.Configuration;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisNoScriptException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Log4j2
public class VoteUpdateProcessor {
    private static final String REDIS_HOST = Configuration.get("redisHost");

    /**
     * The request parameters should be like this:
     * evalsha hash answer:1000:1999 (key1) answer:1000:2000(key2) 2 (entry number for key1) 19998 1 19999 2 1(entry
     * number for key2) 56 3
     */
    private static final String REDIS_INCREMENTAL_UPDATE_SCRIPT = "" +
            "local index = 1\n" +
            "local result = {}\n" +
            "local last = table.getn(ARGV)\n" +
            "for i, key in ipairs(KEYS) do\n" +
            "    local argc = ARGV[index]\n" +
            "    index = index + 1\n" +
            "    local s, e = index, index + argc * 2 - 1\n" +
            "    index = e + 1\n" +
            "    result[i] = {}\n" +
            "    redis.call('hincrby',key,'total',ARGV[last])\n" +
            "    for j = s, e, 2 do\n" +
            "        result[i][j] = redis.call('hincrby',key,ARGV[j],ARGV[j+1])\n" +
            "    end\n" +
            "end\n" +
            "return result\n";
    private static final String REDIS_FULL_UPDATE_SCRIPT = "" +
            "local index = 1\n" +
            "local result = {}\n" +
            "local last = table.getn(ARGV)\n" +
            "for i, key in ipairs(KEYS) do\n" +
            "    redis.call('del',key)\n" +  // Should delete all the records before
            "    local argc = ARGV[index]\n" +
            "    index = index + 1\n" +
            "    local s, e = index, index + argc * 2 - 1\n" +
            "    index = e + 1\n" +
            "    result[i] = {}\n" +
            "    redis.call('hincrby',key,'total',ARGV[last])\n" +
            "    for j = s, e, 2 do\n" +
            "        result[i][j] = redis.call('hset',key,ARGV[j],ARGV[j+1])\n" +
            "    end\n" +
            "end\n" +
            "return result\n";
    private String[] FULL_UPDATE_CONFIG = new String[]{REDIS_FULL_UPDATE_SCRIPT, ""};
    private String[] INCREMENTAL_UPDATE_CONFIG = new String[]{REDIS_INCREMENTAL_UPDATE_SCRIPT, ""};

    public void updateVotesFullQuantity(List<QuestionAnswer> currentAnswers, List<Question> questions, int increaseTotal) {
        // TODO: This should be merged to the lua script, but considering it only called when load from db offline,
        // this is not a big issue;
        if (CollectionUtils.isEmpty(questions)) {
            log.info("The questionnaire contains no question.");
            return;
        }
        updateVotes(null, currentAnswers, questions, Type.FULL, increaseTotal);
    }

    public void updateVotesIncremental(List<QuestionAnswer> formerAnswers, List<QuestionAnswer> newAnswers,
                                       List<Question> questions) {
        int increaseTotal = CollectionUtils.isEmpty(formerAnswers) ? 1 : 0;
        updateVotes(formerAnswers, newAnswers, questions, Type.INCREMENTAL, increaseTotal);
    }

    private void updateVotes(List<QuestionAnswer> formerAnswers, List<QuestionAnswer> newAnswers,
                             List<Question> questions, Type type, int increaseTotal) {
        if (CollectionUtils.isEmpty(newAnswers) || CollectionUtils.isEmpty(questions)) {
            log.warn("The newAnswers is {}, and questions is {}, either is empty", newAnswers, questions);
            return;
        }
        Map<Long, Question> index =
                questions.stream().collect(Collectors.toMap(Question::getId, Function.identity()));
        Map<String, Map<String, Long>> redisEntries = new HashMap<>();
        buildRedisEntries(formerAnswers, index, false, redisEntries);
        buildRedisEntries(newAnswers, index, true, redisEntries);

        log.info("Successfully build the redis entries: {}.", redisEntries);

        List<String> keysToUpdate = new ArrayList<>(redisEntries.size());
        List<String> redisArgvs = new LinkedList<>();
        for (Map.Entry<String, Map<String, Long>> entry : redisEntries.entrySet()) {
            keysToUpdate.add(entry.getKey());
            Map<String, Long> argv = entry.getValue();
            redisArgvs.add(String.valueOf(argv.size()));
            for (Map.Entry<String, Long> argvEntry : argv.entrySet()) {
                redisArgvs.add(argvEntry.getKey());
                redisArgvs.add(String.valueOf(argvEntry.getValue()));
            }
        }

        log.info("Successfully transform redisEntries to redis keys and args.");

        log.info("keys: {}, args {}", keysToUpdate, redisArgvs);

        String[] luaArgvs = prepareRedisArgvs(keysToUpdate, redisArgvs, increaseTotal);
        Jedis jedis = new Jedis(REDIS_HOST);

        updateQuestionsInQuestionnaire(questions, jedis);

        String[] config = getConfig(type);
        Object result;
        try {
            result = jedis.evalsha(config[1], keysToUpdate.size(), luaArgvs);
        } catch (JedisNoScriptException e) {
            config[1] = jedis.scriptLoad(config[0]);
            result = jedis.evalsha(config[1], keysToUpdate.size(), luaArgvs);
            log.warn("The script is loaded successfully, type is {}, the sha is {}", type, config[1]);
        }
        log.info("The update result is: {}", result);
    }

    private void buildRedisEntries(List<QuestionAnswer> answers, final Map<Long, Question> index, boolean increase,
                                   Map<String, Map<String, Long>> redisEntries) {
        if (!CollectionUtils.isEmpty(answers)) {
            answers.stream().forEach(a -> {
                Question question = index.get(a.getQuestionId());
                Pair<String, Map<String, Long>> pair = DbRedisConverter.convert(a.getAnswer(), question);
                String left = pair.getLeft();
                Map<String, Long> right = pair.getRight();
                if (!increase) {
                    for (Map.Entry<String, Long> entry : right.entrySet()) {
                        entry.setValue(-entry.getValue());
                    }
                }
                if (redisEntries.containsKey(left)) {
                    for (Map.Entry<String, Long> entry : right.entrySet()) {
                        Map<String, Long> countMap = redisEntries.get(left);
                        long formerCount = 0;
                        if (countMap.containsKey(entry.getKey())) {
                            formerCount = countMap.get(entry.getKey());
                        }
                        countMap.put(entry.getKey(), formerCount + entry.getValue());
                    }

                } else {
                    redisEntries.put(left, right);
                }
            });
        }
    }

    private String[] prepareRedisArgvs(List<String> keysToUpdate, List<String> redisArgvs, int increaseTotal) {
        int size = keysToUpdate.size() + redisArgvs.size() + 1;
        String[] luaArgvs = new String[size];
        int luaArgvIndex = 0;

        // Must use index to access the array, since the order does matter.
        for (int i = 0; i < keysToUpdate.size(); ++i, ++luaArgvIndex) {
            luaArgvs[luaArgvIndex] = keysToUpdate.get(i);
        }
        for (int i = 0; i < redisArgvs.size(); ++i, ++luaArgvIndex) {
            luaArgvs[luaArgvIndex] = redisArgvs.get(i);
        }
        luaArgvs[size - 1] = String.valueOf(increaseTotal);
        return luaArgvs;
    }

    private String[] getConfig(Type type) {
        switch (type) {
            case FULL:
                return FULL_UPDATE_CONFIG;
            case INCREMENTAL:
                return INCREMENTAL_UPDATE_CONFIG;
            default:
                throw new UnsupportedOperationException("This type is not supported");
        }
    }

    private void updateQuestionsInQuestionnaire(List<Question> questions, Jedis jedis) {
        long questionnaireId = questions.get(0).getQuestionnaireId();
        String key = QuestionnaireReportRepositoryRedis.composePattern(questionnaireId);
        String[] questionIds = new String[questions.size()];
        for (int i = 0; i < questions.size(); ++i) {
            questionIds[i] = String.valueOf(questions.get(i).getId());
        }
        Transaction transaction = jedis.multi();
        transaction.del(key);
        transaction.lpush(key, questionIds);
        transaction.exec();
    }

    private enum Type {
        INCREMENTAL, FULL;
    }
}
