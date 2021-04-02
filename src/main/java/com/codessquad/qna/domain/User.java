package com.codessquad.qna.domain;

import com.codessquad.qna.StringUtils;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String userId;

    private String password;
    private String name;
    private String email;

    protected User() {
    }

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
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

    public boolean isMatchingPassword(String password) {
        return this.password.equals(password);
    }

    public boolean isMatchingId(Long id) {
        return this.id.equals(id);
    }

    public boolean isMatchingUserId(User user) {
        return this.userId.equals(user.userId);
    }

    public boolean isEmpty() {
        return StringUtils.isEmpty(userId) || StringUtils.isEmpty(name) || StringUtils.isEmpty(email) || StringUtils.isEmpty(password);
    }

    public void update(User updateUser) {
        this.password = updateUser.password;
        this.name = updateUser.name;
        this.email = updateUser.email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId);
    }
}
