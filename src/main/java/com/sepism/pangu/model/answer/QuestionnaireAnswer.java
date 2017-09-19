package com.sepism.pangu.model.answer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "questionnaireAnswers")
public class QuestionnaireAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    private long questionnaireId;
    private Date creationDate;
}
