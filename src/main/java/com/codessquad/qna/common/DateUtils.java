package com.codessquad.qna.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

    private DateUtils() {}

    public static String format(LocalDateTime localDateTime) {
        return localDateTime.format(formatter);
    }
}
