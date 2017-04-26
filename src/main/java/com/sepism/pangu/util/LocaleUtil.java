package com.sepism.pangu.util;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.CharEncoding;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

//On windows this file should use GBK encoding, otherwise, there will be a mess code.

//TODO: Replace this class with a service
// This util should be used only for short term when the localed string number is not that many, after we have
// too many strings to localization, we should build a service to do this.
@Log4j2
public class LocaleUtil {
    //The following strings will be shown on login page
    private static final Map<Locale, String> PLEASE_SIGN_IN_001 = new HashMap<>();
    private static final Map<Locale, String> REMEMBER_ME_002 = new HashMap<>();
    private static final Map<Locale, String> FORGET_PASSWORD_003 = new HashMap<>();
    private static final Map<Locale, String> ACCOUNT_NAME_004 = new HashMap<>();
    private static final Map<Locale, String> PASSWORD_005 = new HashMap<>();
    private static final Map<Locale, String> SIGN_IN_006 = new HashMap<>();

    static {
        PLEASE_SIGN_IN_001.put(Locale.CHINESE, "请登录");
        PLEASE_SIGN_IN_001.put(Locale.ENGLISH, "Please Sign In");

        REMEMBER_ME_002.put(Locale.CHINESE, "记住我");
        REMEMBER_ME_002.put(Locale.ENGLISH, "Remember me");

        FORGET_PASSWORD_003.put(Locale.CHINESE, "忘记密码");
        FORGET_PASSWORD_003.put(Locale.ENGLISH, "Forget password?");

        ACCOUNT_NAME_004.put(Locale.CHINESE, "邮箱/手机/用户名");
        ACCOUNT_NAME_004.put(Locale.ENGLISH, "Email/Phone/UserName");

        PASSWORD_005.put(Locale.CHINESE, "密码");
        PASSWORD_005.put(Locale.ENGLISH, "Password");

        SIGN_IN_006.put(Locale.CHINESE, "登录");
        SIGN_IN_006.put(Locale.ENGLISH, "Sign In");
    }

    public static String localize(Locale locale, String stringId) {
        Class<LocaleUtil> clazz = LocaleUtil.class;
        try {
            Field field = clazz.getDeclaredField(stringId);
            field.setAccessible(true);
            Map<Locale, String> map = (Map<Locale, String>) field.get(stringId);
            return new String(map.get(locale).getBytes(Charset.defaultCharset()), CharEncoding.UTF_8);
        } catch (Exception e) {
            log.error("Failed to localize for [{}], in Locale [{}]", stringId, locale, e);
        }
        return stringId;
    }
}
