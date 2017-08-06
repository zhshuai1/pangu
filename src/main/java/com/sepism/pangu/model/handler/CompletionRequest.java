package com.sepism.pangu.model.handler;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CompletionRequest extends Request {
    private Date birthDay;
    private String name;
    private String username;
    private String token;
    private String gender;
}
