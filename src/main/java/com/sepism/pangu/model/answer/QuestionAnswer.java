package com.sepism.pangu.model.answer;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class QuestionAnswer {
    private long id;
    private long questionId;
    private long userId;
    private String answer;
    private Date creationDate;
}
