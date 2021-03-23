package com.codessquad.qna.web.utility;

import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exception.LoginException;
import com.codessquad.qna.web.exception.InvalidUserException;

import javax.servlet.http.HttpSession;

public class SessionUtility {
    private SessionUtility() { }

    public static User findSessionedUser(HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
        if (value == null) {
            throw new LoginException("로그인해주십시오.");
        }
        return (User) value;
    }

    public static void verifySessionUser(User sessionedUser, User user, String errorMessage) {
        if (sessionedUser.getId() != user.getId()) {
            throw new InvalidUserException(errorMessage);
        }
    }
}
