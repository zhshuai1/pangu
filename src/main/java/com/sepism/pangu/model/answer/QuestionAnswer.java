package com.sepism.pangu.model.answer;

import com.sepism.pangu.model.questionnaire.Question;
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
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "questionnaireAnswerId")
    private QuestionnaireAnswer questionnaireAnswer;
    private long userId;
    @Enumerated(EnumType.STRING)
    private Question.Type type;
    private String answer;
    private boolean current;
    private Date creationDate;
    private Date lastUpdateTime;
}
