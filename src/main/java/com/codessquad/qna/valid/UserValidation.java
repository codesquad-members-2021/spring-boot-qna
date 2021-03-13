package com.codessquad.qna.valid;

import com.codessquad.qna.domain.User;

public class UserValidation {

    public static String validUserInfo(User user) {
        validInfo("UserId", user.getUserId());
        validInfo("Password", user.getPassword());
        validInfo("Name", user.getName());
        validInfo("Email", user.getEmail());
        return "complete user null check ";
    }

    private static void validInfo(String entity, String info) {
        if (info == null || info.trim().isEmpty()) {
            String error = String.format("%s is empty", entity);
            throw new NullPointerException(error);
        }
    }

}
