package com.sepism.pangu.model.handler;

import com.google.gson.Gson;
import com.sepism.pangu.constant.ErrorCode;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public final class Response {
    private ErrorCode errorCode;
    private List<String> relatedFields;
    private final static Gson GSON = new Gson();

    public Response(ErrorCode errorCode, String... fields) {
        this.errorCode = errorCode;
        this.relatedFields = Arrays.asList(fields);
    }

    public String serialize() {
        return GSON.toJson(this);
    }
}
