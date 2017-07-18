package com.sepism.pangu.model.answer;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.util.List;

@Getter
@Setter
public class QuestionnaireAnswer {
    @Id
    private long id;
    private long questionnaireId;
    private List<QuestionAnswer> questionAnswers;

}
