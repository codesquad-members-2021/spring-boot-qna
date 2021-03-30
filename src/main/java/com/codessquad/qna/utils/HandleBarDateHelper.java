package com.codessquad.qna.utils;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

import java.time.LocalDateTime;

import static com.codessquad.qna.utils.DateUtil.DATE_AND_TIME_PATTERN;

@HandlebarsHelper
public class HandleBarDateHelper {
    public String date(LocalDateTime time) {
        return time.format(DATE_AND_TIME_PATTERN);
    }
}
