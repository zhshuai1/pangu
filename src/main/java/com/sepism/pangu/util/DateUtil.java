package com.sepism.pangu.util;

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
}
