package com.codessquad.qna.web;

import com.codessquad.qna.web.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUser";

    public static boolean isLoginUser(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    public static User getSessionedUser(HttpSession session) {
        return isLoginUser(session) ?  (User) session.getAttribute(USER_SESSION_KEY) : null;
    }

    public static void setUser(HttpSession session, User user) {
        session.setAttribute(USER_SESSION_KEY, user);
    }

    public static void removeUser(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
    }
}
