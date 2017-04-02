package com.sepism.pangu.model.questionnaire;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Questionnaire {
    private long id;
    private List<Long> questionIds;
}
