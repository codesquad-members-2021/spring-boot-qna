package com.codessquad.qna.web.utils;

import com.codessquad.qna.web.domain.user.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SessionUtils {

    public static User getLoginUser(HttpSession session) {
        return userFromSession(session)
                .orElseThrow(() -> new EntityNotFoundException("You are a guest user, please sign in first"));
    }

    public static boolean isLoginUser(HttpSession session) {
        return userFromSession(session).isPresent();
    }

    private static Optional<User> userFromSession(HttpSession session) {
        User user = (User) session.getAttribute("sessionedUser");
        return Optional.ofNullable(user);
    }

}
