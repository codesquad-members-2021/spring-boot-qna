package com.codessquad.qna.user.dto;

import com.codessquad.qna.user.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserRequest {

    @NotBlank(message = "userId is blank")
    private String userId;

    @NotBlank(message = "password is blank")
    private String password;

    @NotBlank(message = "name is blank")
    private String name;

    @NotBlank(message = "email is blank")
    @Email(message = "email form is inappropriate")
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
