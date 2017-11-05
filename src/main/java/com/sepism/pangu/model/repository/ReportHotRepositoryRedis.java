package com.sepism.pangu.model.repository;

import com.sepism.pangu.util.Configuration;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Optional;
import java.util.Set;

@Component
public class ReportHotRepositoryRedis {
    public static String REPORT_RANK_KEY = "report:rank";
    private static String REDIS_HOST = Configuration.get("redisHost");
    private static String TOTAL_READ = "tr";
    // The read/write request since last update, write means submit;
    private static String PERIOD_READ = "pr";

    public static String composeHotKey(long reprotId) {
        return "report:hot:" + reprotId;
    }

    public void incrReadHotBy(long reprotId, long increment) {
        Jedis jedis = new Jedis(REDIS_HOST);
        jedis.hincrBy(composeHotKey(reprotId), TOTAL_READ, increment);
        jedis.hincrBy(composeHotKey(reprotId), PERIOD_READ, increment);
    }


    // change this api to use lua script
    public void updateHot(long reprotId) {
        Jedis jedis = new Jedis(REDIS_HOST);
        double score = Optional.ofNullable(jedis.zscore(REPORT_RANK_KEY, String.valueOf(reprotId))).orElse(0d);

        long pr = Optional.ofNullable(jedis.hget(composeHotKey(reprotId), PERIOD_READ)).map(Long::parseLong)
                .orElse(0l);

        jedis.hset(composeHotKey(reprotId), PERIOD_READ, "0");
        jedis.zadd(REPORT_RANK_KEY, score / 2 + pr, String.valueOf(reprotId));
    }

    public Set<String> findIdsByRankRange(long start, long end) {
        Jedis jedis = new Jedis(REDIS_HOST);
        return jedis.zrange(REPORT_RANK_KEY, start, end);
    }
}
