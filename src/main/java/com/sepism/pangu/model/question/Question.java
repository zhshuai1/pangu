package com.sepism.pangu.model.question;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "questions")
public class Question {
    private long id;
    private String title;
    private Type type;
    private Date creationDate;

    public enum Type {
        RADIO, CHECKBOX, VALUE, STRING, OBJECT;
    }
}
