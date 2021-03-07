package com.codessquad.qna.config;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@HandlebarsHelper
public class CustomHandlebarsHelper {
    public String formatDate(LocalDateTime date, String pattern){
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(pattern);

        return date.format(dateTimeFormat);
    }
}
