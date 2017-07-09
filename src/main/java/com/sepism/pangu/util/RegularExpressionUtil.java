package com.sepism.pangu.util;

import java.util.regex.Pattern;

public final class RegularExpressionUtil {
    public boolean test(String content, String patternString){
        return Pattern.matches(patternString,content);

    }
}
