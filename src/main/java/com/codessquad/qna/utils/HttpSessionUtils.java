package com.codessquad.qna.utils;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.type.UnauthorizedException;

import javax.servlet.http.HttpSession;

/**
 * Created by 68936@naver.com on 2021-03-17 오후 9:09
 * Blog : https://velog.io/@san
 * Github : https://github.com/sanhee
 */
public class HttpSessionUtils {

    private static final String SESSION_KEY = "sessionedUser";

    public static User getLoginUserOf(HttpSession session){
        HttpSessionUtils.checkValidOf(session);
        checkValidOf(session);
        return (User)session.getAttribute(SESSION_KEY);
    }
    public static void checkValidOf(HttpSession session){
        if(session.getAttribute(SESSION_KEY) == null){
            throw new UnauthorizedException(); // 로그인정보가 유효하지 않을 때
        }
    }

    public static void setAttribute(HttpSession session, User loginUser){
        session.setAttribute(SESSION_KEY,loginUser);
    }
    public static void removeAttribute(HttpSession session){
        session.removeAttribute(SESSION_KEY);
    }

}
