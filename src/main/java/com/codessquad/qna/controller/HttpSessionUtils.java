package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    private static final String USER_SESSION_KEY="sessionUser";

    public static boolean isLogined(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    public static User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(USER_SESSION_KEY);
    }

    public static void removeUserSession(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
    }
}
