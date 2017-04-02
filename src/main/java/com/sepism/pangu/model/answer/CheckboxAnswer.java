package com.sepism.pangu.model.answer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckboxAnswer {
    private List<Long> choiceIds;
    private String comment;
}
