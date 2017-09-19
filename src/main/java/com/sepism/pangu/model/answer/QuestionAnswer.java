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
@Table(name = "questionAnswers")
public class QuestionAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long questionId;
    private long questionnaireId;
    private long questionnaireAnswerId;
    private long userId;
    private String answer;
    private Date creationDate;
}
