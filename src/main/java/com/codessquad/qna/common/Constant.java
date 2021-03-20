package com.codessquad.qna.common;

import java.time.format.DateTimeFormatter;

public class Constant {
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT);

    private Constant() {
    }
}
