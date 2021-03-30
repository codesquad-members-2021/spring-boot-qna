package com.codessquad.qna.web.dto.user;

import com.codessquad.qna.web.domain.user.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CreateUserRequest {

    @NotBlank(message = "UserId is mandatory")
    private String userId;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
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
        return User.build(userId, password)
                .name(name)
                .email(email)
                .build();
    }
}
