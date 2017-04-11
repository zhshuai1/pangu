package com.sepism.pangu.model.question;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="choices")
public class Choice {
    private long id;
    private long questionId;
    private String description;
    private Date creationDate;
    private String createdBy;
}
