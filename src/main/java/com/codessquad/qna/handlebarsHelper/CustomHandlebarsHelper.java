package com.codessquad.qna.handlebarsHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@HandlebarsHelper
public class CustomHandlebarsHelper {

    private final Logger logger = LoggerFactory.getLogger(CustomHandlebarsHelper.class);

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public CharSequence formatDateTime(LocalDateTime time) {
        return time.format(dateTimeFormatter);
    }

}
