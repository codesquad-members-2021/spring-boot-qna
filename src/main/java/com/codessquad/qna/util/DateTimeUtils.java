package com.codessquad.qna.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    private DateTimeUtils() {
    }

    public static String formatDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(dateTimeFormatter);
    }
}
