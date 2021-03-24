package com.codessquad.qna.user;

import com.codessquad.qna.common.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.StringUtils;

public class UserDTO extends BaseDTO {
    private String userId;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String newPassword;

    private String name;
    private String email;

    public UserDTO() {
    }

    public UserDTO(Long id, String userId, String password, String name, String email) {
        super(id);

        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static UserDTO from(User user) {
        if (user == null) {
            return new UserDTO();
        }

        return new UserDTO(
                user.getId(),
                user.getUserId(), user.getPassword(),
                user.getName(),
                user.getEmail()
        );
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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
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

    public boolean hasNewPassword() {
        return StringUtils.hasText(newPassword);
    }

    public User toEntity() {
        return User.builder()
                .setId(getId())
                .setUserId(userId)
                .setPassword(password)
                .setName(name)
                .setEmail(email)
                .build();
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + getId() +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
