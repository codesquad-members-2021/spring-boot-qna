package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @Column(nullable = false, length = 20)
    @JsonProperty
    private String userId;

    @JsonIgnore
    private String password;

    @JsonProperty
    private String name;

    @JsonProperty
    private String email;

    public User() {
    }

    public User(String userId) {
        this.userId = userId;
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

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEqualPassword(String expected) {
        return this.password.equals(expected);
    }

    public boolean isEqualUserId(String expected) {
        return this.userId.equals(expected);
    }

    public boolean isEmpty() {
        if ("".equals(this.userId) || this.userId == null) {
            return true;
        }
        if ("".equals(this.email) || this.email == null) {
            return true;
        }
        if ("".equals(this.password) || this.password == null) {
            return true;
        }
        if ("".equals(this.name) || this.name == null) {
            return true;
        }

        return false;
    }

    public void updateUserInfo(User referenceUser, String newPassword) {
        this.userId = referenceUser.getUserId();
        this.password = newPassword;
        this.name = referenceUser.getName();
        this.email = referenceUser.getEmail();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId.equals(user.userId) && password.equals(user.password)
                && name.equals(user.name) && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, password, name, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
