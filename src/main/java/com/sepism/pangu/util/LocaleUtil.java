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
    //The following strings will be shown on login page(1-20)
    private static final Map<Locale, String> PLEASE_SIGN_IN_001 = new HashMap<>();
    private static final Map<Locale, String> REMEMBER_ME_002 = new HashMap<>();
    private static final Map<Locale, String> FORGET_PASSWORD_003 = new HashMap<>();
    private static final Map<Locale, String> ACCOUNT_NAME_004 = new HashMap<>();
    private static final Map<Locale, String> PASSWORD_005 = new HashMap<>();
    private static final Map<Locale, String> SIGN_IN_006 = new HashMap<>();

    //The following strings will be shown on register page(21-40)
    private static final Map<Locale, String> PLEASE_SIGN_IN_021 = new HashMap<>();
    private static final Map<Locale, String> REMEMBER_ME_022 = new HashMap<>();
    private static final Map<Locale, String> FORGET_PASSWORD_023 = new HashMap<>();
    private static final Map<Locale, String> ACCOUNT_NAME_024 = new HashMap<>();
    private static final Map<Locale, String> PASSWORD_025 = new HashMap<>();
    private static final Map<Locale, String> SIGN_IN_026 = new HashMap<>();

    //The following strings will be shown on index page(41-80)
    private static final Map<Locale, String> SEPISM_041 = new HashMap<>();
    private static final Map<Locale, String> INDEX_042 = new HashMap<>();
    private static final Map<Locale, String> ANSWER_043 = new HashMap<>();
    private static final Map<Locale, String> SEARCH_044 = new HashMap<>();
    private static final Map<Locale, String> ACCOUNT_045 = new HashMap<>();

    static {
        PLEASE_SIGN_IN_001.put(Locale.CHINESE, "���¼");
        PLEASE_SIGN_IN_001.put(Locale.ENGLISH, "Please Sign In");

        REMEMBER_ME_002.put(Locale.CHINESE, "��ס��");
        REMEMBER_ME_002.put(Locale.ENGLISH, "Remember me");

        FORGET_PASSWORD_003.put(Locale.CHINESE, "��������");
        FORGET_PASSWORD_003.put(Locale.ENGLISH, "Forget password?");

        ACCOUNT_NAME_004.put(Locale.CHINESE, "����/�ֻ�/�û���");
        ACCOUNT_NAME_004.put(Locale.ENGLISH, "Email/Phone/UserName");

        PASSWORD_005.put(Locale.CHINESE, "����");
        PASSWORD_005.put(Locale.ENGLISH, "Password");

        SIGN_IN_006.put(Locale.CHINESE, "��¼");
        SIGN_IN_006.put(Locale.ENGLISH, "Sign In");


        SEPISM_041.put(Locale.CHINESE,"���⾵");
        SEPISM_041.put(Locale.ENGLISH,"Sepism");

        INDEX_042.put(Locale.CHINESE,"��ҳ");
        INDEX_042.put(Locale.ENGLISH,"Index");

        ANSWER_043.put(Locale.CHINESE,"�ύ�۵�");
        ANSWER_043.put(Locale.ENGLISH,"Submit Opinion");

        SEARCH_044.put(Locale.CHINESE,"����");
        SEARCH_044.put(Locale.ENGLISH,"Search");

        ACCOUNT_045.put(Locale.CHINESE,"�ҵ��˻�");
        ACCOUNT_045.put(Locale.ENGLISH,"My Account");
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
