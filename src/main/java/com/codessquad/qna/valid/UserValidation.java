package com.codessquad.qna.valid;

import com.codessquad.qna.domain.User;

public class UserValidation {

    public static void validUserInfo(User user) {
        validID(user.getId());
        validUserId(user.getUserId());
        validPasssword(user.getPassword());
        validUserName(user.getName());
        validUserEmail(user.getEmail());

    }

    private static void validUserEmail(String email) {
        if (email.trim().isEmpty()) {
            throw new NullPointerException();
        }
    }

    private static void validUserName(String name) {
        if (name.trim().isEmpty()) {
            throw new NullPointerException();
        }
    }

    private static void validPasssword(String password) {
        if (password.trim().isEmpty()) {
            throw new NullPointerException();
        }
    }

    private static void validUserId(String userId) {
        if (userId.trim().isEmpty()) {
            throw new NullPointerException();
        }
    }

    private static void validID(Long id) {
        if (id == null) {
            throw new NullPointerException();
        }
    }
}
