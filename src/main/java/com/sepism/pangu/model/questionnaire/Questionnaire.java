package com.sepism.pangu.model.questionnaire;

import com.sepism.pangu.model.question.Question;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "questionnaires")
public class Questionnaire {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String titleCn;
    private String titleEn;
    //This is the user-facing description
    private String descriptionCn;
    private String descriptionEn;
    //Default fetchType is lazy for this. If you want it work properly, you should put @Transactional to the usage
    // method; Or set the fetchType to eager.
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "questionnaireId")
    private List<Question> questions;
    private long creator;
    private Date creationDate;
    //This is the internal-facing comment
    private String comment;
}
