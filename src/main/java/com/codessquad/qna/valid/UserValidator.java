package com.codessquad.qna.valid;

import com.codessquad.qna.domain.User;

public class UserValidator {

    public static void validate(User user) {
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

    private static void validId(Long id) {
        if (id == null) {
            throw new NullPointerException();
        }
    }
}
