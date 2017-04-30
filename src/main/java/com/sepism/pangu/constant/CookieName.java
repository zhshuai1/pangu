package com.sepism.pangu.constant;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CookieName {
    public static final String USER = "user";
    public static final String TOKEN = "token";
    public static final String USERNAME = "userName";
    public static final String PASSWORD = "password";
    public static final String LOCALE = "locale";

    public static final Set<String> SIMPLE_COOKIE_NAME = new HashSet<>(Arrays.asList(USER, TOKEN, USERNAME, PASSWORD));
}
