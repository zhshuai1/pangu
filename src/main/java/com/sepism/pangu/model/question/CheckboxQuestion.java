package com.sepism.pangu.model.question;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckboxQuestion extends Question {
    private List<Long> choiceIds;
}
