package com.sepism.pangu.model.handler;

import com.google.gson.Gson;
import com.sepism.pangu.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    protected final static Gson GSON = new Gson();
    private ErrorCode errorCode;
    private String errorMessage;

    public Response(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String serialize() {
        return GSON.toJson(this);
    }
}
