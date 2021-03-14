package com.codessquad.qna.util;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@HandlebarsHelper
public class HandlebarHelper {

    private HandlebarHelper() {}

    private static final String ISO8601 = "yyyy-MM-dd HH:mm";

    public static CharSequence formatDateTime(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern(ISO8601));
    }

}
