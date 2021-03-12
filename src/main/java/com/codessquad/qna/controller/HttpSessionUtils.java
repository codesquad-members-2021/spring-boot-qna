package com.codessquad.qna.controller;

import com.codessquad.qna.model.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {

    public static final String USER_SESSION_KEY = "loginUser";

    public static boolean isLoginUser(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    public static User getUserFromSession(HttpSession session) {
        return isLoginUser(session) ? (User) session.getAttribute(USER_SESSION_KEY) : new User();
    }

}
