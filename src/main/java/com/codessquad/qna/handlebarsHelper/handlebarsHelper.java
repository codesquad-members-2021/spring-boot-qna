package com.codessquad.qna.handlebarsHelper;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@HandlebarsHelper
public class handlebarsHelper {
    public CharSequence rowNumber(int value) {
        return String.valueOf(value + 1);
    }

    public CharSequence formatDateTime(ZonedDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
