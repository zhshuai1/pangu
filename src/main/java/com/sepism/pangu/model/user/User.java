package com.sepism.pangu.model.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nickName;
    private String password;
    private String fullName;
    private String email;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Date birthDay;
    private String educationBackground;
    //@Transient
    //private Address address;
    private int level;
    private int score;
    private Date registerDate;
    private Date lastLoginDate;

    public enum Gender {
        MALE, FEMALE, HERMAPHRODITE;
    }
}
