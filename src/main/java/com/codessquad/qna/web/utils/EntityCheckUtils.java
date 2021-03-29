package com.codessquad.qna.web.utils;

public class EntityCheckUtils {

    private EntityCheckUtils() {
    }

    public static boolean isNotEmpty(String string) {
        return string != null && !string.isEmpty();
    }
}
