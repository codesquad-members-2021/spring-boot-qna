package com.codessquad.qna.controller;

import com.codessquad.qna.exception.InvalidSessionException;
import com.codessquad.qna.repository.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUser";

    public static boolean isLoginUser(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isLoginUser(session)) {
            throw new InvalidSessionException();
        }
        return (User)session.getAttribute(USER_SESSION_KEY);
    }

    public static void checkSessionedUserId(HttpSession session, Long id) {
        User sessionedUser = getUserFromSession(session);
        if (!sessionedUser.matchId(id)) {
            throw new InvalidSessionException();
        }
    }
}
