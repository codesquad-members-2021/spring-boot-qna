package com.codessquad.qna.helper;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@HandlebarsHelper
public class HandleBarHelper {
    public int rowNumber(int index) {
        return index + 1;
    }
}
