package com.codessquad.qna.web.utils;

import com.codessquad.qna.web.users.User;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    private final static String SESSION_KEY_LOGIN_USER = "loginUser";

    private SessionUtil() {
    }

    public static boolean isLoginUser(HttpSession session) {
        return session.getAttribute(SESSION_KEY_LOGIN_USER) != null;
    }

    public static User getLoginUser(HttpSession session) {
        return (User) session.getAttribute(SESSION_KEY_LOGIN_USER);
    }

    public static void setLoginUser(HttpSession session, User user) {
        session.setAttribute(SESSION_KEY_LOGIN_USER, user);
    }

    public static void removeLoginUser(HttpSession session) {
        session.removeAttribute(SESSION_KEY_LOGIN_USER);
    }
}
