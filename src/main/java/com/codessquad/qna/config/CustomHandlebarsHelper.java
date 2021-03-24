package com.codessquad.qna.config;

import com.github.jknack.handlebars.Options;
import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

import java.io.IOException;

@HandlebarsHelper
public class CustomHandlebarsHelper {

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
