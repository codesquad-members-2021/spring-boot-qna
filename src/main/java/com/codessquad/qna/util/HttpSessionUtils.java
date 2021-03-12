package com.codessquad.qna.util;

import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.NotExistLoggedUserInSessionException;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {

    public final static String USER_SESSION_KEY = "sessionUser";

    private HttpSessionUtils() {}

    public static boolean isLoggedUser(HttpSession httpSession) {
        Object sessionUser = httpSession.getAttribute(USER_SESSION_KEY);
        return sessionUser != null;
    }

    /**
     * 세션에 로그인된 사용자가 없으면 NotExistLoggedUserInSession 를 throw
     * 있으면 정상적인 유저를 리턴함 따라서 사용하는 곳에서 get() 을 사용해도됨.
     * @param httpSession
     * @throws NotExistLoggedUserInSessionException
     * @return User
     */
    public static User getUserFromSession(HttpSession httpSession) {
        if(!isLoggedUser(httpSession)) {
            throw new NotExistLoggedUserInSessionException();
        }
        return (User) httpSession.getAttribute(USER_SESSION_KEY);
    }

}
