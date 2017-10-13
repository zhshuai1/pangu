package com.sepism.pangu.model.repository;

import com.sepism.pangu.constant.GlobalConstant;
import com.sepism.pangu.model.authentication.Session;
import com.sepism.pangu.util.Configuration;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * TODO: It seems that jedis is not thread-safe. For workaround, will new Jedis when needed. Should configure a
 * connection pool to solve this problem.
 */
@Component
@Log4j2
public class SessionRepositoryRedis {
    private static final String SESSION_PREFIX = "session:";
    private static final String TOKEN = "token";
    private static final String REDIS_HOST = Configuration.get("redisHost");

    public Session findOne(long userId) {
        Jedis jedis = new Jedis(REDIS_HOST);
        String token = jedis.hget(composeKey(userId), TOKEN);
        return Session.builder().id(userId).token(token).build();
    }

    public void save(Session session) {
        if (session == null) {
            log.error("The session to be saved is null. This is very strange.");
            return;
        }
        Jedis jedis = new Jedis(REDIS_HOST);
        String key = composeKey(session.getId());
        Transaction transaction = jedis.multi();
        transaction.hset(key, TOKEN, session.getToken());
        transaction.pexpire(key, GlobalConstant.SESSION_EXPIRED_TIME);
        List<Object> result = transaction.exec();
        if (result == null) {
            log.error("Failed to exec the transaction when storing session to redis.");
        } else {
            log.info("The transaction exec result is: {}", result);
        }
    }

    private String composeKey(long id) {
        return SESSION_PREFIX + id;
    }
}
