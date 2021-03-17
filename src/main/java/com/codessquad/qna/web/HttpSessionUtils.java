package com.codessquad.qna.web;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NoQuestionException;
import com.codessquad.qna.exception.NoUserException;
import javassist.NotFoundException;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUser";

    public static boolean isLoginUser(HttpSession session) {
        Object sessionedUser = session.getAttribute(USER_SESSION_KEY);
        if (sessionedUser == null) {
            throw new NoUserException();
        }
        return true;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isLoginUser(session)) {
            throw new NoUserException();
        }
        return (User) session.getAttribute(USER_SESSION_KEY);
    }
}
