package com.sepism.pangu.model.questionnaire;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titleCn;
    private String titleEn;
    //This is the user-facing description
    private String descriptionCn;
    private String descriptionEn;
    //Default fetchType is lazy for this. If you want it work properly, you should put @Transactional to the usage
    // method; Or set the fetchType to eager.
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, mappedBy = "questionnaireId")
    @OrderBy(value = "indexInQuestionnaire")
    private List<Question> questions;
    private long creator;
    private Date creationDate;
    //This is the internal-facing comment
    private String comment;
    private long hot;
}
