package com.codessquad.qna.web.utils;

import com.codessquad.qna.web.exceptions.users.NoLoginUserException;
import com.codessquad.qna.web.domain.User;

import javax.servlet.http.HttpSession;

public class SessionUtil {
    private final static String SESSION_KEY_LOGIN_USER = "loginUser";

    private SessionUtil() {
    }

    public static boolean isLoginUser(HttpSession session) {
        return session.getAttribute(SESSION_KEY_LOGIN_USER) != null;
    }

    public static User getLoginUser(HttpSession session) {
        User loginUser = (User) session.getAttribute(SESSION_KEY_LOGIN_USER);
        if (loginUser == null) {
            throw new NoLoginUserException("로그인된 사용자만 이용할 수 있는 기능입니다");
        }
        return loginUser;
    }

    public static void setLoginUser(HttpSession session, User user) {
        session.setAttribute(SESSION_KEY_LOGIN_USER, user);
    }

    public static void removeLoginUser(HttpSession session) {
        session.removeAttribute(SESSION_KEY_LOGIN_USER);
    }
}
