package com.codessquad.qna.common;

import com.codessquad.qna.user.domain.User;
import com.codessquad.qna.user.exception.NotLoggedInException;
import com.codessquad.qna.user.exception.UnauthorizedException;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionUser";

    private HttpSessionUtils() {}

    public static void checkLoggedIn(HttpSession session) {
        getUserAttribute(session);
    }

    // FIXME: 권한 인증과 관련된 부분은 AOP 로 분리해야한다.
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
}
