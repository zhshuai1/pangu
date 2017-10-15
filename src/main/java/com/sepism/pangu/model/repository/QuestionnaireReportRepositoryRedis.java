package com.sepism.pangu.model.repository;

import com.sepism.pangu.model.answer.QuestionReport;
import com.sepism.pangu.model.answer.QuestionnaireReport;
import com.sepism.pangu.util.Configuration;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisNoScriptException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Component
@Log4j2
public class QuestionnaireReportRepositoryRedis {

    private static final String LUA_SCRIPT = "" +
            "   local result = {}                               " +
            "   for i,key in ipairs(KEYS) do                    " +
            "       result[i] = redis.call('hgetall',key)       " +
            "   end                                             " +
            "   return result;                                  ";
    private static String REDIS_HOST = Configuration.get("redisHost");
    private static String SCRIPT_SHA = "";

    public List<String> listQuestions(long questionnaireId) {
        Jedis jedis = new Jedis(REDIS_HOST);
        return jedis.lrange(composePattern(questionnaireId), 0, -1);
    }

    // Do not call QuestionReportRepositoryRedis.findOne for each question. By rewrite the function, we could benefit
    // from the batch operation(using script) for both efficiency and transaction.
    public QuestionnaireReport findOne(long questionnaireId) {
        Jedis jedis = new Jedis(REDIS_HOST);
        List<String> questions = jedis.lrange(composePattern(questionnaireId), 0, -1);
        int numberOfQuestions = questions.size();
        String[] keyArray = new String[numberOfQuestions];
        for (int i = 0; i < numberOfQuestions; ++i) {
            keyArray[i] = QuestionReportRepositoryRedis.composeKey(questionnaireId, Long.parseLong(questions.get(i)));
        }
        // Here is one response sample: `[[556, 2, 667, 3], [555, 2, 666, 3]]`
        List<List<String>> rawQuestionnaireReport;
        try {
            rawQuestionnaireReport = (List<List<String>>) jedis.evalsha(SCRIPT_SHA, numberOfQuestions, keyArray);
        } catch (JedisNoScriptException e) {
            SCRIPT_SHA = jedis.scriptLoad(LUA_SCRIPT);
            log.warn("The script is not loaded, will load the script. The sha is {}.", SCRIPT_SHA);
            rawQuestionnaireReport = (List<List<String>>) jedis.evalsha(SCRIPT_SHA, numberOfQuestions, keyArray);
        }
        List<QuestionReport> questionReports = new LinkedList<>();
        for (int i = 0; i < rawQuestionnaireReport.size(); ++i) {
            List<String> rawQuestionReport = rawQuestionnaireReport.get(i);
            QuestionReport questionReport = QuestionReport.builder()
                    .questionnaireId(questionnaireId)
                    // The assumption here is that the redis response order is same with request key order. It should
                    // be.
                    .questionId(Long.parseLong(questions.get(i)))
                    .counts(new HashMap<>()).build();
            for (int j = 0; j < rawQuestionReport.size(); ) {
                questionReport.getCounts().put(rawQuestionReport.get(j++), rawQuestionReport.get(j++));
            }
            questionReports.add(questionReport);
        }

        return QuestionnaireReport.builder()
                .questionnaireId(questionnaireId)
                .questionReports(questionReports).build();
    }

    private String composePattern(long questionnaireId) {
        return "qsInQn:" + questionnaireId;
    }
}
