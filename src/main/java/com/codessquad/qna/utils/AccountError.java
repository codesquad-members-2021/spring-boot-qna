package com.codessquad.qna.utils;

public enum AccountError {

    // 회원가입 - 중복아이디 에러
    DUPLICATED_ID("이미 사용중인 아이디입니다."),
    // 로그인 - 아이디나 비밀번호 불일치 에러
    LOGIN_FAILED("아이디 또는 비밀번호가 틀립니다. 다시 로그인 해주세요.");

    private final String errorMessage;

    AccountError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
