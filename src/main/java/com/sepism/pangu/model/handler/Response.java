package com.sepism.pangu.model.handler;

import com.google.gson.Gson;
import com.sepism.pangu.constant.ErrorCode;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    protected final static Gson GSON = new Gson();
    private ErrorCode errorCode;
    private String errorMessage;
    private Map<String, String> extraData;

    public Response(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public Response(ErrorCode errorCode, String errorMessage) {
        this(errorCode, errorMessage, null);
    }

    public String serialize() {
        return GSON.toJson(this);
    }

}
