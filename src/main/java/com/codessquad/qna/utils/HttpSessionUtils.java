package com.codessquad.qna.utils;

import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.model.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUser";

    public static void isLoginUser(HttpSession session) {
        if (session.getAttribute(USER_SESSION_KEY) == null) {
            throw new IllegalUserAccessException();
        }
    }

    public static User getUserFromSession(HttpSession session) {
        isLoginUser(session);
        return (User)session.getAttribute(USER_SESSION_KEY);
    }
}
