package com.codessquad.qna.domain.dto;

import com.codessquad.qna.domain.User;

public class UserDto {

    private final Long id;
    private final String userId;
    private final String name;
    private final String email;

    public UserDto(User user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
