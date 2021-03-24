package com.codessquad.qna.web.dto.user;

import com.codessquad.qna.web.domain.user.User;

public class CreateUserRequest {

    private String userId;
    private String password;
    private String name;
    private String email;

    protected CreateUserRequest() {
    }

    public CreateUserRequest(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User toEntity(){
        return new User.Builder(userId, password)
                .name(name)
                .email(email)
                .build();
    }
}
