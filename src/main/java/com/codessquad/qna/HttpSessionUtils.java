package com.codessquad.qna;

import com.codessquad.qna.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUser";

    public static boolean isLoginUser(HttpSession session) {
        Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
        return sessionedUser != null;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isLoginUser(session)) {
            throw new IllegalArgumentException("User Not Logged In");
        }
        return (User) session.getAttribute(USER_SESSION_KEY);
    }
}
