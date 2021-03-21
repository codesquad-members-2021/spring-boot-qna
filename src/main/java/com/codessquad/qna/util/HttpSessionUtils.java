package com.codessquad.qna.util;

import com.codessquad.qna.domain.dto.UserDto;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {

    public static final String USER_SESSION_KEY = "sessionedUser";

    private HttpSessionUtils() {
    }

    public static UserDto getUserFromSession(HttpSession session) {
        if (!isLoginUser(session)) {
            throw new IllegalArgumentException("로그인 되어 있지 않음");
        }
        return (UserDto) session.getAttribute(USER_SESSION_KEY);
    }

    public static void updateSessionUser(UserDto user, HttpSession session) {
        session.setAttribute(USER_SESSION_KEY, user);
    }

    private static boolean isLoginUser(HttpSession session) {
        Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
        return sessionedUser != null;
    }
}
