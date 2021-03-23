package com.codessquad.qna.web;

import com.codessquad.qna.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionUser";

    public static boolean isLoginUser(HttpSession session) {
        Object sessionUser = session.getAttribute(USER_SESSION_KEY);

        return sessionUser != null;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isLoginUser(session)) {
            throw new IllegalStateException("로그인 되어있지 않습니다. 로그인을 먼저 한 후, 시도해주세요.");
        }
        return (User) session.getAttribute(USER_SESSION_KEY);
    }
}
