package com.codessquad.qna.domain.user;

import com.codessquad.qna.exception.UserNotFoundException;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static String USER_SESSION_KEY = "sessionedUser";

    public static boolean isLoggedInUser(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    public static User getUserFromSession(HttpSession session) {
        if(!isLoggedInUser(session)) {
            throw new UserNotFoundException();
        }

        return (User)session.getAttribute(USER_SESSION_KEY);
    }
}
