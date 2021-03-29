package com.codessquad.qna.web.utils;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

import java.time.LocalDateTime;

import static com.codessquad.qna.web.utils.DateConstants.MY_DATE_FORMATTER;

@HandlebarsHelper
public class HandlebarDateHelper {

    public String date(LocalDateTime date) {
        if (date == null) {
            return "";
        }
        return date.format(MY_DATE_FORMATTER);
    }
}
