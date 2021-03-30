package com.codessquad.qna.web;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.IllegalUserInfoException;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionUser";

    public static boolean isLoginUser(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isLoginUser(session)) {
            throw new IllegalUserInfoException();
        }
        return (User) session.getAttribute(USER_SESSION_KEY);
    }
}
