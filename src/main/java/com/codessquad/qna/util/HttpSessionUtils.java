package com.codessquad.qna.util;

import javax.servlet.http.HttpSession;

import com.codessquad.qna.domain.User;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionUser";

    public static boolean isLoginUser(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    public static User getSessionUser(HttpSession session) {
        if (!isLoginUser(session)) {
            return null;
        }
        return (User) session.getAttribute(USER_SESSION_KEY);
    }

}
