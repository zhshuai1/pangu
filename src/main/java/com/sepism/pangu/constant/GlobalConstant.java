package com.sepism.pangu.constant;

import java.util.concurrent.TimeUnit;

public final class GlobalConstant {
    public static long SESSION_EXPIRED_TIME = TimeUnit.MINUTES.toMillis(30);
    public static int COOKIE_EXPIRED_TIME = (int) TimeUnit.DAYS.toSeconds(3);
    public static long VALIDATIONCODE_EXPIRED_TIME = TimeUnit.MINUTES.toMillis(15);
    // If the user submit one questionnaire or question twice in less than a week, it will override the former one
    // than create a new one. This is somewhat fraud check
    public static long DEFAULT_UPDATE_INTERVAL = TimeUnit.DAYS.toMillis(7);
}
