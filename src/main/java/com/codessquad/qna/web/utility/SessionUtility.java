package com.codessquad.qna.web.utility;

import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exception.UnAuthenticatedLoginException;
import com.codessquad.qna.web.exception.UnauthorizedUserException;

import javax.servlet.http.HttpSession;

public class SessionUtility {
    public static final String SESSIONED_USER = "sessionedUser";

    private SessionUtility() { }

    public static User findSessionedUser(HttpSession session) {
        Object value = session.getAttribute(SESSIONED_USER);
        if (value == null) {
            throw new UnAuthenticatedLoginException(UnAuthenticatedLoginException.MUST_LOGIN);
        }
        return (User) value;
    }

    public static void verifySessionUser(User sessionedUser, User user, String errorMessage) {
        if (!sessionedUser.equals(user)) {
            throw new UnauthorizedUserException(errorMessage);
        }
    }

    public static void setUser(User user, HttpSession session) {
        session.setAttribute(SESSIONED_USER, user);
    }

    public static void deleteUser(HttpSession session) {
        session.removeAttribute(SESSIONED_USER);
    }

}
