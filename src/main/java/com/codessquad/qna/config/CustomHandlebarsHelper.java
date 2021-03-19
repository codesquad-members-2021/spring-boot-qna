package com.codessquad.qna.config;

import com.codessquad.qna.common.Const;
import com.github.jknack.handlebars.Options;
import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

import java.io.IOException;
import java.time.LocalDateTime;

@HandlebarsHelper
public class CustomHandlebarsHelper {

    public String formatDateTime(LocalDateTime date) {
        return date.format(Const.DEFAULT_DATE_TIME_FORMATTER);
    }

    public Object ifEquals(Object s1, Object s2, Options options) throws IOException {
        Options.Buffer buffer = options.buffer();
        if (s1 == null || !s1.equals(s2)) {
            buffer.append(options.inverse());
        } else {
            buffer.append(options.fn());
        }
        return buffer;
    }
}
