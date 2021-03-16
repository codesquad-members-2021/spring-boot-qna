package com.codessquad.qna.config;

import com.codessquad.qna.domain.DisplayStatus;
import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@HandlebarsHelper
public class HandlebarsHelpers {
    public boolean checkStatus(final Object obj1){
        return obj1 == DisplayStatus.OPEN;
    }
}
