package com.sepism.pangu.model.handler;

import com.sepism.pangu.constant.ErrorCode;

public class CompletionResponse extends Response {
    public CompletionResponse(ErrorCode errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
