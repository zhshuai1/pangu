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
