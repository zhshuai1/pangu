package com.sepism.pangu.model.authentication;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "validationcodes")
public class ValidationCode {
    @Id
    private String phone;
    private String code;
    private Date lastUpdateTime;
}
