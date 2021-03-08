package com.codessquad.qna.util;

import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.NotExistLoggedUserInSession;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class HttpSessionUtils {

    public final static String USER_SESSION_KEY = "sessionUser";

    public static boolean isLoggedUser(HttpSession httpSession) {
        Object sessionUser = httpSession.getAttribute(USER_SESSION_KEY);
        return sessionUser != null;
    }

    /**
     * 세션에 로그인된 사용자가 없으면 NotExistLoggedUserInSession 를 throw
     * 있으면 정상적인 유저를 리턴함 따라서 사용하는 곳에서 get() 을 사용해도됨.
     * @param httpSession
     * @return
     */
    public static Optional<User> getUserFromSession(HttpSession httpSession) {
        if(!isLoggedUser(httpSession)){
            throw new NotExistLoggedUserInSession();
        }
        return Optional.of((User) httpSession.getAttribute(USER_SESSION_KEY));
    }

}
