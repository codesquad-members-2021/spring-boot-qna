package com.codessquad.qna.valid;

import com.codessquad.qna.domain.User;

public class UserValidation {

    public static void validUserInfo(User user) {
        validUserId(user.getUserId());
        validPasssword(user.getPassword());
        validUserName(user.getName());
        validUserEmail(user.getEmail());
    }

    private static void validUserEmail(String email) {
        if (email.trim().isEmpty()) {
            throw new NullPointerException("이메일이 비었습니다.");
        }
    }

    private static void validUserName(String name) {
        if (name.trim().isEmpty()) {
            throw new NullPointerException("이름이 비었습니다.");
        }
    }

    private static void validPasssword(String password) {
        if (password.trim().isEmpty()) {
            throw new NullPointerException("비밀번호가 비었습니다.");
        }
    }

    private static void validUserId(String userId) {
        if (userId.trim().isEmpty()) {
            throw new NullPointerException("아이디가 비었습니다.");
        }
    }

}
