package com.sepism.pangu.model.register;

import com.sepism.pangu.model.handler.Request;
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
