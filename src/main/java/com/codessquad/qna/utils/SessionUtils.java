package com.codessquad.qna.utils;

import com.codessquad.qna.user.User;
import com.codessquad.qna.exception.UserNotFoundException;

import javax.servlet.http.HttpSession;

public class SessionUtils {
    private static final String SESSION_USER = "sessionedUser";

    private SessionUtils() {
    }

    public static User getSessionUser(HttpSession session) {
        User sessionUser = ((User) session.getAttribute(SESSION_USER));

        if (sessionUser == null) {
            throw new UserNotFoundException();
        }

        return sessionUser;
    }

    public static void setSessionUser(HttpSession session, User user) {
        session.setAttribute(SESSION_USER, user);
    }

    public static void removeSessionUser(HttpSession session) {
        session.removeAttribute(SESSION_USER);
    }
}
