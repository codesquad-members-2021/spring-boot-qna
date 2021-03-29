package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class User extends AbstractEntity{
    @JsonProperty
    @Column(nullable = false, length = 20, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @JsonProperty
    private String name;

    @JsonProperty
    private String email;

    public boolean isMatchingPassword(String password) {
        return this.password.equals(password);
    }

    public boolean isMatchingPassword(User user) {
        System.out.println(user.password);
        return this.password.equals(user.password);
    }

    public void update(User updateUser, String newPassword) {
        this.userId = updateUser.userId;
        this.email = updateUser.email;
        this.name = updateUser.name;
        this.password = newPassword;
    }

    public boolean checkNull(User user) {
        return user.userId == null
                || user.password == null
                || user.email == null
                || user.name == null;
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
