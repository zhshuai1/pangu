package com.sepism.pangu.model.repository;

import com.sepism.pangu.constant.GlobalConstant;
import com.sepism.pangu.model.authentication.Session;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;

@Component
@Log4j2
public class SessionRepositoryRedis {
    private static final String SESSION_PREFIX = "session:";
    private static final String TOKEN = "token";
    @Autowired
    private Jedis jedis;

    public Session findOne(long userId) {
        List<String> elements = jedis.hmget(SESSION_PREFIX + userId, TOKEN);
        return Session.builder().id(userId).token(elements.get(0)).build();
    }

    public void save(Session session) {
        if (session == null) {
            log.error("The session to be saved is null. This is very strange.");
            return;
        }
        jedis.hset(SESSION_PREFIX + session.getId(), TOKEN, session.getToken());
        jedis.pexpire(SESSION_PREFIX + session.getId(), GlobalConstant.SESSION_EXPIRED_TIME);
    }
}
