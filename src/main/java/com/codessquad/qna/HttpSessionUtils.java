package com.codessquad.qna;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.UnauthenticatedException;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {

    public static final String USER_SESSION_KEY = "sessionedUser";

    public static boolean isLoginUser(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isLoginUser(session)) {
            throw new UnauthenticatedException("로그인이 필요합니다.");
        }
        return (User) session.getAttribute(USER_SESSION_KEY);
    }
}
