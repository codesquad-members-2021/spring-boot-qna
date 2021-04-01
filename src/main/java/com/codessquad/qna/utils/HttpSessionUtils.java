package com.codessquad.qna.utils;

import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.model.dto.UserDto;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUser";

    public static void isLoginUser(HttpSession session) {
        if (session.getAttribute(USER_SESSION_KEY) == null) {
            throw new IllegalUserAccessException();
        }
    }

    public static UserDto getUserDtoFromSession(HttpSession session) {
        isLoginUser(session);
        return (UserDto)session.getAttribute(USER_SESSION_KEY);
    }
}
