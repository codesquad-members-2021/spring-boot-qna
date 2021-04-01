package com.codessquad.qna.web.exceptions.users;

public class RequestToCreateDuplicatedUserException extends RuntimeException {
    public static final String DUPLICATED_ID = "중복된 아이디입니다. 다른 아이디로 가입해주세요";

    public RequestToCreateDuplicatedUserException(String message) {
        super(message);
    }
}
