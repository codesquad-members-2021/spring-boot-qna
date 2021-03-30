package com.codessquad.qna.web.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String stringOf(LocalDateTime datetime) {
        return datetime.format(DATE_TIME_FORMATTER);
    }
}
