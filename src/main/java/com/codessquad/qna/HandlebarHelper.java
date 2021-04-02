package com.codessquad.qna;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@HandlebarsHelper
public class HandlebarHelper {

    private static final String ISO8601 = "yyyy-MM-dd HH:mm";

    private HandlebarHelper() {
    }

    public static CharSequence formattedDateTime(LocalDateTime createdAt) {
        return createdAt.format(DateTimeFormatter.ofPattern(ISO8601));
    }
}
