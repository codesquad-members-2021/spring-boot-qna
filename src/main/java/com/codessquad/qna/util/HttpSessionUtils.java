package com.codessquad.qna.util;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotLoggedInException;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionUser";

    public static void checkSessionUser(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new NotLoggedInException();
        }
    }

    public static User getSessionUser(HttpSession session) {
        return HttpSessionUtils.getUserFromSession(session);
    }

    public static void checkAccessibleSessionUser(User sessionUser, Question question) {
        if (!question.isEqualWriter(sessionUser)) {
            throw new NotLoggedInException("자신의 질문만 수정 및 삭제가 가능합니다.");
        }
    }

    public static void checkAccessibleSessionUser(User sessionUser, Answer answer) {
        if (!answer.isEqualWriter(sessionUser)) {
            throw new NotLoggedInException("자신이 쓴 답글만 수정 및 삭제가 가능합니다.");
        }
    }

    public static void checkAccessibleSessionUser(User sessionUser, User user) {
        if (!user.equals(sessionUser)) {
            throw new NotLoggedInException("자신의 정보만 수정 및 삭제가 가능합니다.");
        }
    }

    public static boolean isLoginUser(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isLoginUser(session)) {
            return null;
        }

        return (User) session.getAttribute(USER_SESSION_KEY);
    }
}
