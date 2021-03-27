package com.codessquad.qna.web.exceptions.users;

public class NoLoginUserException extends RuntimeException {
    public static final String ONLY_FOR_LOGGED_IN_USER = "로그인된 사용자만 이용할 수 있는 기능입니다";

    public NoLoginUserException(String message) {
        super(message);
    }
}
