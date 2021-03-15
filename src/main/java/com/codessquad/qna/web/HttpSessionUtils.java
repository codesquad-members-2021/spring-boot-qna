package com.codessquad.qna.web;

import com.codessquad.qna.web.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUser";

    public static boolean isLoginUser(HttpSession session) {
        Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
        if (sessionedUser == null) {
            return false;
        }
        return true;
    }

    public static User getSessionedUser(HttpSession session) {
        //여기 optional 활용법 없나
        if (!isLoginUser(session)) {
            return null;
        }
        return (User) session.getAttribute(USER_SESSION_KEY);

    }
}
