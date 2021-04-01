package com.codessquad.qna.model.dto;

import com.codessquad.qna.exception.EntityNotCreateException;
import com.codessquad.qna.model.User;

public class UserDto {

    private Long id;

    private String userId;

    private String password;

    private String name;

    private String email;

    public UserDto() {}

    public UserDto(User user) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public User toEntity() {
        if (userId == null || password == null || name == null || email == null) {
            throw new EntityNotCreateException();
        }
        return new User(id, userId, password, name, email);
    }

    public void update(UserDto userDto) {
        this.password = userDto.password;
        this.name = userDto.name;
        this.email = userDto.email;
    }

    public boolean matchId(Long id) {
        return this.id.equals(id);
    }

    public boolean matchUserId(String userId) {
        return this.userId.equals(userId);
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
