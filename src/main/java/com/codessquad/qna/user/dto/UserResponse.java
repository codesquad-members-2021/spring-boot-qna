package com.codessquad.qna.user.dto;

import com.codessquad.qna.user.domain.User;

public class UserResponse {
    private Long id;
    private String userId;
    private String password;
    private String name;
    private String email;

    protected UserResponse() {}

    public UserResponse(Long id, String userId, String password, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static UserResponse of(User user) {
        return new UserResponse(
                user.getId(),
                user.getUserId(),
                user.getPassword(),
                user.getName(),
                user.getEmail());
    }

    public Long getId() {
        return id;
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
}
