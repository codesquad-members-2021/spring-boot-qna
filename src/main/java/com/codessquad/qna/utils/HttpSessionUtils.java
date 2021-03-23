package com.codessquad.qna.utils;

import com.codessquad.qna.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {

    public static final String USER_SESSION_KEY = "sessionedUser";

    public static boolean isLoginUser(HttpSession session) {
        User user = (User)session.getAttribute(USER_SESSION_KEY);

        return user != null;
    }

    public static User getUserFromSession (HttpSession session) {
        if(!isLoginUser(session)) {
            return null;
        }

        User sessionedUser = (User)session.getAttribute(USER_SESSION_KEY);

        return sessionedUser;
    }

    public static void setUserSessionKey(HttpSession session, User user) {
        session.setAttribute(USER_SESSION_KEY, user);
    }

    public static void removeSession(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
    }
}
