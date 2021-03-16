package com.codessquad.qna.util;

import com.codessquad.qna.domain.User;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionUser";

    public static boolean isLoginUser(HttpSession session){
        Object tempUser = session.getAttribute(USER_SESSION_KEY);
        if (tempUser == null){
            return false;
        }
        return true;
    }

    public static User getUserFromSession(HttpSession session){
        if(!isLoginUser(session)){
            return null;
        }

        return (User) session.getAttribute(USER_SESSION_KEY);
    }
}
