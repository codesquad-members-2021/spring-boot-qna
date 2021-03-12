package com.codessquad.qna.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CanNotFindPostException extends RuntimeException {

    public CanNotFindPostException() {
        super("해당 POST 를 찾을 수 없습니다.");
    }
}
