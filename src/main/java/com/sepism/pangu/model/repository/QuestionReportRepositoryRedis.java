package com.sepism.pangu.model.repository;

import com.sepism.pangu.model.answer.QuestionReport;
import com.sepism.pangu.util.Configuration;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.Map;

@Component
@Log4j2
public class QuestionReportRepositoryRedis {
    private static String REDIS_HOST = Configuration.get("redisHost");

    public static String composeKey(long questionnaireId, long questionId) {
        return "answer:" + questionnaireId + ":" + questionId;
    }

    public QuestionReport findOne(long questionId) {
        Jedis jedis = new Jedis(REDIS_HOST);
        QuestionReport questionReport = QuestionReport.builder().questionnaireId(0).questionId(questionId).build();
        Map<String, String> counts = jedis.hgetAll(composeKey(0, questionId));
        questionReport.setCounts(counts);
        return questionReport;
    }
}
