package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotLoggedInException;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {

    private static final String USER_SESSION_KEY = "sessionUser";

    private HttpSessionUtils() {
    }

    public static boolean isLoggedIn(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    public static User loginUser(HttpSession session) throws NotLoggedInException {
        User user = (User) session.getAttribute(USER_SESSION_KEY);
        if (user == null) {
            throw new NotLoggedInException();
        }
        return user;
    }

    public static void removeUserSession(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
    }

    public static void setUserSession(HttpSession session, User user) {
        session.setAttribute(USER_SESSION_KEY, user);
    }
}
