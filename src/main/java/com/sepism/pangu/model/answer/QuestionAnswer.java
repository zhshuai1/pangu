package com.sepism.pangu.model.answer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionAnswer {
    private long id;
    private long questionId;
    private long userId;
}
