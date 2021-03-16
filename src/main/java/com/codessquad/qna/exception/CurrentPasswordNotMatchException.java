package com.codessquad.qna.exception;

import com.codessquad.qna.model.User;

public class CurrentPasswordNotMatchException extends RuntimeException{

    private final User user;

    public CurrentPasswordNotMatchException(User user) {
        super("기존 비밀번호가 일치하지 않습니다.");
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
