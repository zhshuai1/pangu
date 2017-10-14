package com.sepism.pangu.model.answer;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    private Date lastUpdateTime;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "questionnaireAnswer")
    private List<QuestionAnswer> questionAnswers;
}
