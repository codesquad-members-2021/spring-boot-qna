package com.codessquad.qna.web.utility;

import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exception.UnAuthenticatedLoginException;
import com.codessquad.qna.web.exception.UnauthorizedUserException;

import javax.servlet.http.HttpSession;

public class SessionUtility {
    public static final String SESSIONED_USER = "sessionedUser";

    private SessionUtility() { }

    public static User findSessionedUser(HttpSession session) {
        Object potentialLoginUser = session.getAttribute(SESSIONED_USER);
        if (potentialLoginUser == null) {
            throw new UnAuthenticatedLoginException(UnAuthenticatedLoginException.MUST_LOGIN);
        }
        return (User) potentialLoginUser;
    }

    public static void setUser(User user, HttpSession session) {
        session.setAttribute(SESSIONED_USER, user);
    }

    public static void deleteUser(HttpSession session) {
        session.removeAttribute(SESSIONED_USER);
    }

}
