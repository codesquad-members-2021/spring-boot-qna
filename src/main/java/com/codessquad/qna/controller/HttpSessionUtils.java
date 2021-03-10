package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionUser";

    private HttpSessionUtils() {
    }

    public static boolean isLoginUser(HttpSession session) {
        return getSessionUser(session) != null;
    }

    public static User getSessionUser(HttpSession session) {
        return (User) session.getAttribute(USER_SESSION_KEY);
    }
}

