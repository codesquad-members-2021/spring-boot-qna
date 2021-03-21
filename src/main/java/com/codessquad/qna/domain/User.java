package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
public class User extends AbstractEntity {

    @Column(nullable = false, length = 20, unique = true)
    @JsonProperty
    private String userId;

    @JsonIgnore
    private String password;

    @JsonProperty
    private String name;

    @JsonProperty
    private String email;

    protected User() {
    }

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
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

    public boolean matchesPassword(String oldPassword) {
        return this.password.equals(oldPassword);
    }

    public void update(User newUserInfo) {
        this.password = newUserInfo.password;
        this.name = newUserInfo.name;
        this.email = newUserInfo.email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) && Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), userId);
    }
}
