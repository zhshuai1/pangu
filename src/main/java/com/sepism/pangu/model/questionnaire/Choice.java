package com.sepism.pangu.model.questionnaire;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="choices")
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long questionId;
    private String descriptionCn;
    private String descriptionEn;
    private Date creationDate;
    private long creator;
}
