package com.sepism.pangu.constant;

import java.util.concurrent.TimeUnit;

public final class GlobalConstant {
    public static long SESSION_EXPIRED_TIME = TimeUnit.MINUTES.toMillis(30);
    public static int COOKIE_EXPIRED_TIME = (int) TimeUnit.DAYS.toSeconds(3);
    public static long VALIDATIONCODE_EXPIRED_TIME = TimeUnit.MINUTES.toMillis(15);

}
