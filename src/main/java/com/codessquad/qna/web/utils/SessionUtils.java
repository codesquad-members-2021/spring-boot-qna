package com.codessquad.qna.web.utils;

import com.codessquad.qna.web.domain.user.User;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class SessionUtils {

    public static Optional<User> getLoginUser(HttpSession session){
        Object user = session.getAttribute("sessionedUser");
        return Optional.ofNullable((User) user);
    }

    public static boolean isLoginUser(HttpSession session){
        return getLoginUser(session).isPresent();
    }


}
