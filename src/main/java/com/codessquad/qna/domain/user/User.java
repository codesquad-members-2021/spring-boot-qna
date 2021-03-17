package com.codessquad.qna.domain.user;

import com.codessquad.qna.domain.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class User extends AbstractEntity {

    @Column(nullable = false, length = 20, unique = true)
    @JsonProperty
    private String userId;

    private String password;

    @JsonProperty
    private String name;

    @JsonProperty
    private String email;

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

    public boolean isCorrectPassword(String password) {
        return this.password.equals(password);
    }


    public void update(User user) {
        this.name = user.name;
        this.password = user.password;
        this.email = user.email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
