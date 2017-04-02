package com.sepism.pangu.model.question;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Question {
    private long id;
    private String title;
    private Type type;
    private Date creationDate;

    public enum Type {
        RADIO, CHECKBOX, VALUE, STRING, OBJECT;
    }
}
