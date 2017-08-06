package com.sepism.pangu.model.user;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private long originAddress;
    private long currentAddress;
    private boolean isCompleted;
    private int level;
    private int score;
    private Date registerDate;
    private Date lastLoginDate;

    public enum Gender {
        MALE, FEMALE, HERMAPHRODITE;
    }
}
