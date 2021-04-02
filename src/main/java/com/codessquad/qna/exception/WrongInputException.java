package com.codessquad.qna.exception;

public class WrongInputException extends RuntimeException {
    public static final String WRONG_USER_INPUT = "사용자로부터 잘못된 입력(중복된 유저 아이디로 회원가입/허용되지 않은 입력 )받았습니다 ";
    public WrongInputException() {
        super(WRONG_USER_INPUT);
    }
}
