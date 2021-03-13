package com.codessquad.qna.utils;

import com.codessquad.qna.user.User;
import com.codessquad.qna.exception.UserNotFoundException;

import javax.servlet.http.HttpSession;

public class SessionUtils {
    private SessionUtils() {
    }

    public static User getSessionUser(HttpSession session) {
        User sessionUser = ((User) session.getAttribute("sessionedUser"));

        if (sessionUser == null) {
            throw new UserNotFoundException();
        }

        return sessionUser;
    }
}
