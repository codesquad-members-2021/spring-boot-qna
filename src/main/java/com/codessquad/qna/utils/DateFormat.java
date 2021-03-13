package com.codessquad.qna.utils;

import java.time.format.DateTimeFormatter;

public class DateFormat {

    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm";

    public static final DateTimeFormatter DEFAULT = DateTimeFormatter.ofPattern(DateFormat.DEFAULT_FORMAT);

    private DateFormat() {

    }
}
