package com.codessquad.qna.user.dto;

import com.codessquad.qna.user.domain.User;

import java.time.LocalDateTime;

import static com.codessquad.qna.common.DateUtils.format;

public class UserResponse {
    private Long id;
    private String userId;
    private String name;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    protected UserResponse() {}

    public UserResponse(Long id, String userId, String name, String email, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getUserId(),
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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getFormattedCreatedDate() {
        return format(createdDate);
    }

    public String getFormattedModifiedDate() {
        return format(modifiedDate);
    }
}
