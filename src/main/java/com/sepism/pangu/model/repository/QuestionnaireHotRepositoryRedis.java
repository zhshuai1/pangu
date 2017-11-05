package com.sepism.pangu.model.repository;

import com.sepism.pangu.util.Configuration;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class QuestionnaireHotRepositoryRedis {
    public static String QUESTIONNAIRE_RANK_KEY = "qn:rank";
    private static String REDIS_HOST = Configuration.get("redisHost");
    private static String TOTAL_READ = "tr";
    private static String TOTAL_WRITE = "tw";
    // The read/write request since last update, write means submit;
    private static String PERIOD_READ = "pr";
    private static String PERIOD_WRITE = "pw";

    public static String composeHotKey(long questionnaireId) {
        return "qn:hot:" + questionnaireId;
    }

    public void incrReadHotBy(long questionnaireId, long increment) {
        Jedis jedis = new Jedis(REDIS_HOST);
        jedis.hincrBy(composeHotKey(questionnaireId), TOTAL_READ, increment);
        jedis.hincrBy(composeHotKey(questionnaireId), PERIOD_READ, increment);
    }

    public void incrWriteHotBy(long questionnaireId, long increment) {
        Jedis jedis = new Jedis(REDIS_HOST);
        jedis.hincrBy(composeHotKey(questionnaireId), TOTAL_WRITE, increment);
        jedis.hincrBy(composeHotKey(questionnaireId), PERIOD_WRITE, increment);
    }

    // change this api to use lua script
    public void updateHot(long questionnaireId) {
        Jedis jedis = new Jedis(REDIS_HOST);
        double score = Optional.ofNullable(jedis.zscore(QUESTIONNAIRE_RANK_KEY, String.valueOf(questionnaireId)))
                .orElse(0d);

        long pr = Optional.ofNullable(jedis.hget(composeHotKey(questionnaireId), PERIOD_READ)).map(Long::parseLong)
                .orElse(0l);
        long pw = Optional.ofNullable(jedis.hget(composeHotKey(questionnaireId), PERIOD_WRITE)).map(Long::parseLong)
                .orElse(0l);
        jedis.hset(composeHotKey(questionnaireId), PERIOD_READ, "0");
        jedis.hset(composeHotKey(questionnaireId), PERIOD_WRITE, "0");
        jedis.zadd(QUESTIONNAIRE_RANK_KEY, score / 2 + pr + pw, String.valueOf(questionnaireId));
    }

    public List<Long> findIdsByRankRange(long start, long end) {
        Jedis jedis = new Jedis(REDIS_HOST);
        return jedis.zrange(QUESTIONNAIRE_RANK_KEY, start, end).stream()
                .map(Long::parseLong).collect(Collectors.toList());
    }
}
