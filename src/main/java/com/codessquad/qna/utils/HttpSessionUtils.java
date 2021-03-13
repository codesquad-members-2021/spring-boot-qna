package com.codessquad.qna.utils;

import com.codessquad.qna.domain.user.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUser";

    private HttpSessionUtils() {

    }

    public static boolean isLoginUser(HttpSession session) {
        User user = (User) session.getAttribute(USER_SESSION_KEY);
        return user != null;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isLoginUser(session)) {
            return null;
        }
        return (User) session.getAttribute(USER_SESSION_KEY);
    }
}
