package com.codessquad.qna.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
// 얼럿으로 왜 실패했는제
// location >> 자바스크립트 코드
// location.href = "something" 스크립트 코드 두개로 커버 가능!
