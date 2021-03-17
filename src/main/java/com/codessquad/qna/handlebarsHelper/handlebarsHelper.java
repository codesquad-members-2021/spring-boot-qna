package com.codessquad.qna.handlebarsHelper;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@HandlebarsHelper
public class handlebarsHelper {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public CharSequence formatDateTime(LocalDateTime time) {
        return time.format(dateTimeFormatter);
    }

}
