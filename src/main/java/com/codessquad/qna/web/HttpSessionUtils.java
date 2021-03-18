package com.codessquad.qna.web;

import com.codessquad.qna.exception.NoSessionedUserException;
import com.codessquad.qna.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUser";

    public static boolean isLoginUser(HttpSession session) {
        Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
        if (sessionedUser == null) {
            throw new NoSessionedUserException();
        }
        return true;
    }

    public static User getUserFromSession(HttpSession session) {
        isLoginUser(session);
        return (User) session.getAttribute(USER_SESSION_KEY);
    }
}
