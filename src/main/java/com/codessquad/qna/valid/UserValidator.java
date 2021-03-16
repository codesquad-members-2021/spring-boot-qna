package com.codessquad.qna.valid;

import com.codessquad.qna.domain.User;

public class UserValidator {

    public static void validate(User user) {
        validInfo(user.getUserId());
        validInfo(user.getPassword());
        validInfo(user.getName());
        validInfo(user.getEmail());

    }

    private static void validInfo(String info) {
        if (info == null || info.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private static void validId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException();
        }
    }
}
