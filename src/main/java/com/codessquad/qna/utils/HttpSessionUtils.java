package com.codessquad.qna.utils;

import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.exception.NotAuthorizationException;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUser";

    private HttpSessionUtils() {

    }

    public static boolean isLoginUser(HttpSession session) {
        User user = (User) session.getAttribute(USER_SESSION_KEY);
        if (user == null) {
            throw new NotAuthorizationException();
        }
        return true;
    }

    public static User getUserFromSession(HttpSession session) {
        isLoginUser(session);
        return (User) session.getAttribute(USER_SESSION_KEY);
    }
}