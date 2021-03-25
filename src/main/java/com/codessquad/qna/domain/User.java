package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import javax.persistence.*;

@Entity
public class User {
    @Id
    @JsonProperty
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
        return this.password.equals(user.password);
    }

    public boolean isMatchingId(long id) {
        return this.id == id;
    }

    public void update(User updateUser, String newPassword) {
        this.userId = updateUser.userId;
        this.email = updateUser.email;
        this.name = updateUser.name;
        this.password = newPassword;
    }

    public boolean checkEmpty(User user) {
        return user.userId == null
                || user.password == null
                || user.email == null
                || user.name == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId);
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
