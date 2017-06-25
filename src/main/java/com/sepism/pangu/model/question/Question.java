package com.sepism.pangu.model.question;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private Type type;
    // We will make all choices as a json string and then store it.
    private String choices;
    private long questionnaireId;
    private Date creationDate;
    private long creator;

    public enum Type {
        RADIO, CHECKBOX, VALUE, STRING, OBJECT;
    }
}
