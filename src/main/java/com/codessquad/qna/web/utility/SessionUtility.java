package com.codessquad.qna.web.utility;

import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exception.LoginException;
import com.codessquad.qna.web.exception.UserException;

import javax.servlet.http.HttpSession;

public class SessionUtility {
    private SessionUtility() {}

    public static User findSessionedUser(HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
        if(value == null) {
            throw new LoginException("로그인해주십시오.");
        }
        return (User)value;
    }

    public static boolean verifySessionUser(User sessionedUser, User user) {
        if(sessionedUser.getId() != user.getId()) {
            throw new UserException("본인의 회원정보만 수정할 수 있습니다.");
        }
        return true;
    }
}
