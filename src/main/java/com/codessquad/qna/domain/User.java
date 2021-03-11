package com.codessquad.qna.domain;

import javax.persistence.*;

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

    public boolean sameAs(User user) {
        if (!this.userId.equals(user.getUserId())) {
            return false;
        }
        if (!this.password.equals(user.getPassword())) {
            return false;
        }
        if (!this.name.equals(user.getName())) {
            return false;
        }
        if (!this.email.equals(user.getEmail())) {
            return false;
        }

        return true;
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
