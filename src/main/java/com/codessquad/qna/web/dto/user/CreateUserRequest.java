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

    //TODO : 빌더 구현하면 new User -> builder로 User 인스턴스 생성
    public User toEntity(){
        return new User(userId, password, name, email);
    }
}
