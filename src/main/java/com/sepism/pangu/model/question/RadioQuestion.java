package com.sepism.pangu.model.question;

import com.sepism.pangu.model.question.Question;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class RadioQuestion extends Question {
    private List<Long> choiceIds;
    private Date creationDate;
}
