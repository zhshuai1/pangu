package com.sepism.pangu.model.questionnaire;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "choices")
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long questionId;
    private long parent;
    private long root;
    private String descriptionCn;
    private String descriptionEn;
    private Date creationDate;
    private long creator;
    private String comment;
}
