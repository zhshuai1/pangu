package com.sepism.pangu.model.answer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class QuestionReport {
    private long questionnaireId;
    private long questionId;
    private Map<String, String> counts;
}
