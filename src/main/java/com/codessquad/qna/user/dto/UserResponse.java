package com.codessquad.qna.user.dto;

import com.codessquad.qna.user.domain.User;

import java.time.LocalDateTime;

public class UserResponse {
    private Long id;
    private String userId;
    private String password;
    private String name;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    protected UserResponse() {}

    public UserResponse(Long id, String userId, String password, String name, String email, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static UserResponse of(User user) {
        return new UserResponse(
                user.getId(),
                user.getUserId(),
                user.getPassword(),
                user.getName(),
                user.getEmail(),
                user.getCreatedDate(),
                user.getModifiedDate());
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }
}
