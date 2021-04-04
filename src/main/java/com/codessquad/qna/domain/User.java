package com.codessquad.qna.domain;

import javax.persistence.*;

@Entity
public class User extends AbstractEntity {

    @Column(nullable = false, length = 20, unique = true)
    private String userId;

    private String name;
    private String password;
    private String email;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean matchPassword(String passwordToMatch) {
        return this.password.equals(passwordToMatch);
    }

    public boolean matchId(Long idToMatch) {
        return super.getId().equals(idToMatch);
    }

    public User updateProfile(User updatedUser) {
        if (updatedUser.password.isEmpty()) {
            this.password = updatedUser.password;
        }
        this.name = updatedUser.name;
        this.email = updatedUser.email;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                super.toString() +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
