package com.codessquad.qna.utils;

import com.codessquad.qna.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;

public class SessionUtils {
    private static final String SESSION_USER = "sessionedUser";

    private SessionUtils() {
    }

    public static User getSessionUser(HttpSession session) {
        User sessionUser = ((User) session.getAttribute(SESSION_USER));

        if (sessionUser == null) {
            throw HttpClientErrorException.create(
                    "로그인이 필요합니다.",
                    HttpStatus.UNAUTHORIZED,
                    "",
                    null,
                    null,
                    StandardCharsets.UTF_8
            );
        }

        return sessionUser;
    }

    public static void setSessionUser(HttpSession session, User user) {
        session.setAttribute(SESSION_USER, user);
    }

    public static void removeSessionUser(HttpSession session) {
        session.removeAttribute(SESSION_USER);
    }
}
