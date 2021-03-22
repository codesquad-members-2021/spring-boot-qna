package com.codessquad.qna.utils;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotFoundException;

import javax.servlet.http.HttpSession;
import java.util.logging.Handler;

public class SessionUtil {

    private static final String SESSION_KEY_LOGIN_USER = "loginUser";

    private SessionUtil() {

    }

    public static boolean isLoginUser(HttpSession session) {
        return session.getAttribute(SESSION_KEY_LOGIN_USER) !=null;
    }

    public static User getLoginUser(HttpSession session) {
        User loginUser = (User) session.getAttribute(SESSION_KEY_LOGIN_USER);
        if(loginUser == null) {
            throw new NotFoundException();
        }
        return loginUser;
    }

    public static void setLoginUser(HttpSession session ,User user) {
        session.setAttribute(SESSION_KEY_LOGIN_USER,user);
    }
    public static void removeLoginUser(HttpSession session ,User user) {
        session.removeAttribute(SESSION_KEY_LOGIN_USER);
    }

}
