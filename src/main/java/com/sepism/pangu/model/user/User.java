package com.sepism.pangu.model.user;

import com.sepism.pangu.model.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class User {
    private long id;
    private String nickName;
    private String password;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private Date birthDay;
    private String educationBackground;
    private Address address;
    private int level;
    private int score;
    private Date registerDate;
    private Date lastLoginDate;

    public enum Gender {
        MALE, FEMALE, HERMAPHRODITE;
    }
}
