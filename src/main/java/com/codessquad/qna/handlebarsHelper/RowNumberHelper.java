package com.codessquad.qna.handlebarsHelper;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@HandlebarsHelper
public class RowNumberHelper {
    public CharSequence rowNumber(int value) {
        return String.valueOf(value + 1);
    }
}
