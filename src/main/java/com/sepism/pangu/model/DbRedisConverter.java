package com.sepism.pangu.model;

import com.google.gson.Gson;
import com.sepism.pangu.model.answer.QuestionAnswer;
import com.sepism.pangu.model.questionnaire.Question;
import com.sepism.pangu.model.repository.QuestionReportRepositoryRedis;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public final class DbRedisConverter {
    private static Gson GSON = new Gson();

    public static Pair<String, Map<String, Long>> convert(QuestionAnswer questionAnswer, Question question) {
        if (questionAnswer == null || question == null) {
            log.warn("The questionAnswer is {} and the question is {}", questionAnswer, question);
            return null;
        }
        return convert(questionAnswer.getAnswer(), question);
    }

    public static Pair<String, Map<String, Long>> convert(String answer, Question question) {
        if (answer == null || question == null) {
            log.warn("The answer to convert is {} and the question is {}", answer, question);
            return null;
        }
        long questionnaireId = question.getQuestionnaireId();
        long questionId = question.getId();
        if (StringUtils.isBlank(answer)) {
            return null;
        }
        String redisKey = QuestionReportRepositoryRedis.composeKey(questionnaireId, questionId);
        Pair<String, Map<String, Long>> result = new ImmutablePair<>(redisKey, new HashMap<>());
        Map<String, Long> right = result.getRight();
        // TODO: need to support more types
        switch (question.getType()) {
            case RADIO:
            case INTEGER:
                right.put(answer, 1l);
                break;
            case CHECKBOX:
                List<String> choices = GSON.fromJson(answer, List.class);
                choices.stream().forEach(c -> right.put(c, 1l));
                break;
            default:
                break;
        }
        return result;
    }
}
