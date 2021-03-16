package com.codessquad.qna.web.utils;

import org.springframework.stereotype.Component;
import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@HandlebarsHelper
public class HandlebarDateHelper {
    private static final DateTimeFormatter MY_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public CharSequence date(LocalDateTime localDateTime) {
        return localDateTime.format(MY_DATE_FORMATTER);
    }
}
