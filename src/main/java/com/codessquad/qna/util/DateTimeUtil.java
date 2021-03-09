package com.codessquad.qna.util;

import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static DateTimeFormatter getTimeFormatter() {
        return DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm");
    }
}
