package com.codessquad.qna.user.dto;

import com.codessquad.qna.common.BaseResponse;
import com.codessquad.qna.user.domain.User;

public class UserResponse extends BaseResponse {
    private String userId;
    private String name;
    private String email;

    public UserResponse(Builder builder) {
        super(builder);
        this.userId = builder.userId;
        this.name = builder.name;
        this.email = builder.email;
    }

    private static class Builder extends BaseResponse.Builder<Builder> {
        private String userId;
        private String name;
        private String email;

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        protected UserResponse build() {
            return new UserResponse(this);
        }

        private Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        private Builder name(String name) {
            this.name = name;
            return this;
        }

        private Builder email(String email) {
            this.email = email;
            return this;
        }
    }

    public static UserResponse from(User user) {
        Builder builder = new Builder()
                .id(user.getId())
                .createdDate(user.getCreatedDateTime())
                .modifiedDate(user.getModifiedDateTime())
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail());
        return new UserResponse(builder);
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
