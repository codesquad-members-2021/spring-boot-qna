package com.codessquad.qna.web.utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeConstant {
    public static final DateTimeFormatter DATE_PATTERN
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private TimeConstant() {}

}
