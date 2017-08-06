package com.sepism.pangu.model.handler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest extends Request {
    private String type;
    private String username;
    private String validationCode;
    private String password;
}
