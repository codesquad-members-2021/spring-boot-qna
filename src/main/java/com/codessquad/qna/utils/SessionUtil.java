package com.codessquad.qna.utils;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotFoundException;

import javax.servlet.http.HttpSession;
import java.security.MessageDigest;

public class SessionUtil {

    private static String SESSION_KEY_LOGIN_USER;

    private static String generateSessionKey(User user) throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        MessageDigest mDigest = MessageDigest.getInstance("MD5");

        mDigest.update((user.getUserId() + Long.toString(user.getId())).getBytes());
        byte[] msgStr = mDigest.digest() ;

        for(int i=0; i < msgStr.length; i++){
            String tmpEncTxt = Integer.toHexString((int)msgStr[i] & 0x00ff) ;
            stringBuffer.append(tmpEncTxt) ;
        }
        return stringBuffer.toString() ;
    }

    private SessionUtil() {

    }

    public static boolean isValidUser(HttpSession session, User OwnerUser) {
        return OwnerUser.equals((User)session.getAttribute(SESSION_KEY_LOGIN_USER));
    }

    public static boolean isLoginUser(HttpSession session) {
        return session.getAttribute(SESSION_KEY_LOGIN_USER) != null;
    }

    public static User getLoginUser(HttpSession session) {
        User loginUser = (User) session.getAttribute(SESSION_KEY_LOGIN_USER);
        if (loginUser == null) {
            throw new NotFoundException();
        }
        return loginUser;
    }

    public static void setLoginUser(HttpSession session, User user) {
        //try {
            SESSION_KEY_LOGIN_USER = "loginUser";
            session.setAttribute(SESSION_KEY_LOGIN_USER, user);
        //}
        //catch (Exception e) {

        //}
    }

    public static void removeLoginUser(HttpSession session) {
        session.removeAttribute(SESSION_KEY_LOGIN_USER);
    }

}
