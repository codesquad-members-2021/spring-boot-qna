package com.codessquad.qna.util;

import com.codessquad.qna.dto.UserDto;

public class UserValidation {

    private UserValidation() {
    }

    public static void validate(UserDto userDto) {
        validateUserId(userDto.getUserId());
        validatePassword(userDto.getUserId());
        validateName(userDto.getName());
    }

    private static void validateUserId(String userId) {
        if (userId.isEmpty() || userId.length() > 20) {
            throw new IllegalArgumentException("아이디 입력이 유효하지 않습니다.");
        }
    }

    private static void validatePassword(String password) {
        if (password.isEmpty()) {
            throw new IllegalArgumentException("비밀번호 입력이 유효하지 않습니다.");
        }
    }

    private static void validateName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("이름 입력이 유효하지 않습니다.");
        }
    }

}
