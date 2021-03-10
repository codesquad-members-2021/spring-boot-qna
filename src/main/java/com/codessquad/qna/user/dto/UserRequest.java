package com.codessquad.qna.user.dto;

import com.codessquad.qna.user.domain.User;

public class UserRequest {
    private String userId;
    private String password;
    private String name;
    private String email;

    protected UserRequest() {}

    public UserRequest(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static UserRequest of(User user) {
        return new UserRequest(
                user.getUserId(),
                user.getPassword(),
                user.getName(),
                user.getEmail());
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public User toUser() {
        return new User(userId, password, name, email);
    }
}
