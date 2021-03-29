package com.codessquad.qna.web.utils;

import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exceptions.users.NoLoginUserException;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.web.exceptions.users.NoLoginUserException.ONLY_FOR_LOGGED_IN_USER;

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
            throw new NoLoginUserException(ONLY_FOR_LOGGED_IN_USER);
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
