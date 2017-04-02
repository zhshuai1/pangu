package com.sepism.pangu.model.questionnaire;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class QuestionnaireAnswer {
    private long id;
    private long userId;
    private long questionnaireId;
    private List<Long> questionAnswerIds;
    private Date creationDate;
}
