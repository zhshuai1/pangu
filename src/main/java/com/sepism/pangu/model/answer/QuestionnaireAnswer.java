package com.sepism.pangu.model.answer;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "questionnaireAnswers")
public class QuestionnaireAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long userId;
    private long questionnaireId;
    private boolean current;
    private Date creationDate;
}
