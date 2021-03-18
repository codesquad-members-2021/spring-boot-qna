package com.codessquad.qna.util;

import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.UserNotFoundInSessionException;

import javax.servlet.http.HttpSession;

public class HttpSessionUtil {
    public static final String USER_KEY = "sessionedUser";

    public static boolean hasUser(HttpSession session) {
        Object userObject = session.getAttribute(USER_KEY);
        return userObject != null && userObject instanceof User;
    }

    public static User getUser(HttpSession session) {
        Object userObject = session.getAttribute(USER_KEY);
        if (hasUser(session)) {
            return (User) userObject;
        }
        throw new UserNotFoundInSessionException();
    }
}
