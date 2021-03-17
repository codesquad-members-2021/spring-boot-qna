package com.codessquad.qna.util;

import com.codessquad.qna.entity.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class HttpSessionUtil {
    public static final String USER_KEY = "sessionedUser";

    public static Optional<User> getUser(HttpSession session) {
        Object userObject = session.getAttribute(USER_KEY);
        if (userObject != null && userObject instanceof User) {
            return Optional.of((User) session.getAttribute(USER_KEY));
        }
        return Optional.empty();
    }
}
