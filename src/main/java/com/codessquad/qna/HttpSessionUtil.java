package com.codessquad.qna;

import com.codessquad.qna.domain.User;

import javax.servlet.http.HttpSession;

/**
 * Created by 68936@naver.com on 2021-03-17 오후 9:09
 * Blog : https://velog.io/@san
 * Github : https://github.com/sanhee
 */
public class HttpSessionUtil {

    private static final String SESSION_KEY = "sessionedUser";

    public static User getLoginUser(HttpSession session){
        if(!checkValidOfSession(session)){
            throw new RuntimeException("나중에 예외처리");
        }
        return (User)session.getAttribute(SESSION_KEY);
    }
    public static boolean checkValidOfSession(HttpSession session){
        return session.getAttribute(SESSION_KEY) != null;
    }

    public static void setAttribute(HttpSession session, User loginUser){
        session.setAttribute(SESSION_KEY,loginUser);
    }
    public static void removeAttribute(HttpSession session){
        session.removeAttribute(SESSION_KEY);
    }

}
