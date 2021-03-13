package com.codessquad.qna.handlebarsHelper;

import com.codessquad.qna.domain.Answer;
import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@HandlebarsHelper
public class handlebarsHelper {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public CharSequence formatDateTime(LocalDateTime time) {
        return time.format(dateTimeFormatter);
    }

    public CharSequence countAnswerByNotDeleted(List<Answer> answers) {
        int count = 0;
        for (Answer answer : answers) {
            if (!answer.isDeleted()) {
                count++;
            }
        }
        return String.valueOf(count);
    }
}
