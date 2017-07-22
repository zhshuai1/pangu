package com.sepism.pangu.constant;


public final class RegularExpression {
    public static final String USERNAME_PATTERN = "^[a-zA-Z0-9\\-_\\.@]{6,20}$";
    public static final String PASSWORD_PATTERN = "^[a-zA-Z0-9\\-_\\.!@#$%^&*()]{6,20}$";
    public static final String PHONE_PATTERN = "^[0-9]{11,}$";
}
