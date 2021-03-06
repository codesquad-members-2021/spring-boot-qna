package com.codessquad.qna.util;

import com.codessquad.qna.entity.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class HttpSessionUtils {

    public final static String USER_SESSION_KEY = "sessionUser";

    public static boolean isLoggedUser(HttpSession httpSession) {
        Object sessionUser = httpSession.getAttribute(USER_SESSION_KEY);
        return sessionUser != null;
    }

    public static Optional<User> getUserFromSession(HttpSession httpSession) {
        if(!isLoggedUser(httpSession)){
            return Optional.empty();
        }
        return Optional.of((User) httpSession.getAttribute(USER_SESSION_KEY));
    }

}
