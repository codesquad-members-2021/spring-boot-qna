package com.codessquad.qna.helper;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@HandlebarsHelper
public class HandlebarHelper {
    public int incremented(int index) {
        return index + 1;
    }
}
