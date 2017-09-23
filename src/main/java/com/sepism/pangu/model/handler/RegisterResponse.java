package com.sepism.pangu.model.handler;

import com.sepism.pangu.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse extends Response {
    private String userId;
    private String token;

    public RegisterResponse(ErrorCode errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
