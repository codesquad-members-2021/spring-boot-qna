package com.codessquad.qna.util;

import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.UserNotFoundInSessionException;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_KEY = "sessionedUser";

    private HttpSessionUtils() {
    }

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

    public static boolean isAuthorized(Long userId, HttpSession session) {
        User user = getUser(session);
        return userId == user.getId();
    }
}
