package com.sepism.pangu.model.questionnaire;

import com.sepism.pangu.model.questionnaire.Choice;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String titleCn;
    private String titleEn;
    @Enumerated(EnumType.STRING)
    private Type type;
    private boolean required;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "questionId")
    private List<Choice> choices;
    private long questionnaireId;
    private Date creationDate;
    private long creator;

    public enum Type {
        RADIO, CHECKBOX, VALUE, TEXT, OBJECT, DATE, ADDRESS, EMAIL, PHONE;
    }
}
