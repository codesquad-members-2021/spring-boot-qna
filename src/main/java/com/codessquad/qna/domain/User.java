package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class User extends AbstractEntity {
    @JsonProperty
    @Column(nullable = false, length = 20, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @JsonProperty
    private String name;

    @JsonProperty
    private String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public User() {

    }

    public boolean isMatchingPassword(String password) {
        return this.password.equals(password);
    }

    public void update(String userId, String password, String name, String email, String newPassword) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        if (!newPassword.equals("")) {
            this.password = newPassword;
        }
    }

    public boolean checkEmpty(User user) {
        return user.userId.equals("")
                || user.password.equals("")
                || user.email.equals("")
                || user.name.equals("");
    }

    public boolean isMatchingId(long id) {
        return this.getId() == id;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
