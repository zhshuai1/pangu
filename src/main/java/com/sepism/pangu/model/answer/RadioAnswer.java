package com.sepism.pangu.model.answer;

import com.sepism.pangu.model.answer.QuestionAnswer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RadioAnswer extends QuestionAnswer {
    private long choiceId;
    //if the user choose other, then comment will be available
    private String comment;
}
