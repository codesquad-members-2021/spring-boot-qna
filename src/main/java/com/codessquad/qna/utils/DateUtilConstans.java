package com.codessquad.qna.utils;

import java.time.format.DateTimeFormatter;

public class DateUtilConstans {
    private DateUtilConstans() {
    }

    public static final DateTimeFormatter DATE_AND_TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
}
