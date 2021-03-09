package com.codessquad.qna.web.utils;

import com.codessquad.qna.web.users.User;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    private final static String SESSION_KEY_USER_OBJECT = "loginUser";

    private SessionUtil() {
    }

    public static boolean isLoginUser(HttpSession session) {
        return session.getAttribute(SESSION_KEY_USER_OBJECT) != null;
    }

    public static User getSessionUser(HttpSession session) {
        return (User) session.getAttribute(SESSION_KEY_USER_OBJECT);
    }

    public static void setSessionUser(HttpSession session, User user) {
        session.setAttribute(SESSION_KEY_USER_OBJECT, user);
    }

    public static void removeSessionUser(HttpSession session) {
        session.removeAttribute(SESSION_KEY_USER_OBJECT);
    }
}
