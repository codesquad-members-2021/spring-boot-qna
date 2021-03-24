package com.codessquad.qna.web.utils;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@HandlebarsHelper
public class HandlebarDateHelper {
    private static final DateTimeFormatter MY_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public String date(LocalDateTime date) {
        if (date == null) {
            return "";
        }
        return date.format(MY_DATE_FORMATTER);
    }
}
