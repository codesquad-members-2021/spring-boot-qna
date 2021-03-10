package com.codessquad.qna.controller;

import javax.servlet.http.HttpSession;
import com.codessquad.qna.domain.User;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "user";

    public static boolean isLoginUser(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    public static User getSessionUser(HttpSession session) {
        if (!isLoginUser(session)){
            return null;
        }
        return (User) session.getAttribute(USER_SESSION_KEY);
    }

}
