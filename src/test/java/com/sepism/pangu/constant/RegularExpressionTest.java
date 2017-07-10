package com.sepism.pangu.constant;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegularExpressionTest {
    private String usernamePattern = RegularExpression.USERNAME_PATTERN;
    private String passwordPattern = RegularExpression.PASSWORD_PATTERN;

    @Test
    public void testRegularUsername() {
        assertTrue(Pattern.matches(usernamePattern, "000000"));
        assertTrue(Pattern.matches(usernamePattern, "123456"));
        assertTrue(Pattern.matches(usernamePattern, "zhangsh"));
        assertTrue(Pattern.matches(usernamePattern, "zh_ang_ok@yeah.net"));
        assertTrue(Pattern.matches(usernamePattern, "13269336957"));
        assertTrue(Pattern.matches(usernamePattern, "zhshuai1"));
        assertTrue(Pattern.matches(usernamePattern, "12dsjfSDJKF._-@"));
    }

    @Test
    public void testIrregularUsername() {
        assertFalse(Pattern.matches(usernamePattern, "000"));
        assertFalse(Pattern.matches(usernamePattern, "sdfjk#"));
        assertFalse(Pattern.matches(usernamePattern, "hello world"));
        assertFalse(Pattern.matches(usernamePattern, "9876543210abcdefghijk"));
        assertFalse(Pattern.matches(usernamePattern, "zhshuai1<"));
    }

    @Test
    public void testRegularPassword() {
        assertTrue(Pattern.matches(passwordPattern, "000000"));
        assertTrue(Pattern.matches(passwordPattern, "123456"));
        assertTrue(Pattern.matches(passwordPattern, "zhangsh"));
        assertTrue(Pattern.matches(passwordPattern, "zh_ang_ok@yeah.net"));
        assertTrue(Pattern.matches(passwordPattern, "13269336957"));
        assertTrue(Pattern.matches(passwordPattern, "zhshuai1"));
        assertTrue(Pattern.matches(passwordPattern, "12dfKF._-@!@#$%^&*()"));
    }

    @Test
    public void testIrregularPassword() {
        assertFalse(Pattern.matches(passwordPattern, "000"));
        assertFalse(Pattern.matches(passwordPattern, "hello world"));
        assertFalse(Pattern.matches(passwordPattern, "9876543210abcdefghijk"));
        assertFalse(Pattern.matches(passwordPattern, "zhshuai1<"));
    }

}
