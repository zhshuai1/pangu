package com.sepism.pangu.constant;

// For the backend APIs, we will only return the error code to the frontend, and frontend should translate the error
// code to readable messages.
public enum ErrorCode {
    SUCCESS, EMPTY_VALUE, INVALID_VALUE, USER_EXIST, INTERNAL_ERROR;
}
