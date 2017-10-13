package com.sepism.pangu.model.answer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class QuestionnaireReport {
    private long questionnaireId;
    private List<QuestionReport> questionReports;
}
