package com.codessquad.qna.web.utils;

import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final DateTimeFormatter MY_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    private DateUtil() {
    }
}
