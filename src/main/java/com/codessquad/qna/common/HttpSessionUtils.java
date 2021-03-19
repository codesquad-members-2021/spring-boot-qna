package com.codessquad.qna.common;

import com.codessquad.qna.user.domain.User;
import com.codessquad.qna.user.exception.NotLoggedInException;
import com.codessquad.qna.user.exception.UnauthorizedException;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class HttpSessionUtils {
    private static final String USER_SESSION_KEY = "sessionUser";

    private HttpSessionUtils() {}

    public static void checkLoggedIn(HttpSession session) {
        getUserAttribute(session);
    }
    
    public static void checkAuthorization(Long id, HttpSession session) {
        boolean authorized = getUserAttribute(session).matchId(id);
        if (!authorized) {
            throw new UnauthorizedException("허가받지 않은 사용자입니다.");
        }
    }

    public static User getUserAttribute(HttpSession session) {
        return (User) Optional.ofNullable(session.getAttribute(USER_SESSION_KEY))
                .orElseThrow(() -> new NotLoggedInException("로그인 되어있지 않습니다."));
    }

    public static void setUserAttribute(User user, HttpSession session) {
        session.setAttribute(USER_SESSION_KEY, user);
    }

    public static void clearSession(HttpSession session) {
        /**
         * NOTE: 앞으로 User 외의 정보를 세션에 추가한다면
         * 이 메서드에서 전부 삭제하도록 한다.
         */
        session.removeAttribute(USER_SESSION_KEY);
        session.invalidate();
    }
}
