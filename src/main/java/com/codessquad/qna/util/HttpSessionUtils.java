package com.codessquad.qna.util;

import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.NotExistLoggedUserInSessionException;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {

    public final static String USER_SESSION_KEY = "sessionUser";

    private HttpSessionUtils() {}

    /**
     * 유저가 로그인 되어있는지를 판단하는 메소드로, 로그인된 유저가 없다면 false 를 리턴한다.
     * @param httpSession
     * @return 세션에 로그인한 유저가 있다면 True, 없으면 False 를 리턴한다.
     */
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
