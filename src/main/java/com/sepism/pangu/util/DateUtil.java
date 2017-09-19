package com.sepism.pangu.util;

import org.apache.commons.lang3.Validate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public final class DateUtil {
    private static final String dateFormat = "YYYY-MM-dd HH:mm:ss.SSS";
    private final static DateTimeFormatter formatter = DateTimeFormat.forPattern(dateFormat).withZoneUTC();


    public static Date fromString(String dateString) {
        return formatter.parseDateTime(dateString).toDate();
    }

    public static String toString(Date date) {
        return formatter.print(date.getTime());
    }

    public static long diff(Date date1, Date date2) {
        Validate.notNull(date1);
        Validate.notNull(date2);
        return date1.getTime() - date2.getTime();
    }
}
