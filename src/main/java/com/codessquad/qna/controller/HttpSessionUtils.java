package com.codessquad.qna.controller;

import com.codessquad.qna.exception.UserSessionException;
import com.codessquad.qna.model.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {

    public static final String USER_SESSION_KEY = "loginUser";

    public static boolean isLoginUser(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isLoginUser(session)) {
            throw new UserSessionException("로그인이 필요한 서비스입니다.");
        }
        return (User) session.getAttribute(USER_SESSION_KEY);
    }

}
