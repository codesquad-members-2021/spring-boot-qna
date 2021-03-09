package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class HttpSessionUtils {
    private static final String USER_SESSION_KEY="sessionUser";

    public static boolean isLogined(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    public static Optional<User> getUserFromSession(HttpSession session) {
        return Optional.ofNullable((User)session.getAttribute(USER_SESSION_KEY));
    }

    public static void removeUserSession(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
    }
}
