package com.codessquad.qna.web.domain;

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

    public boolean isMatchingPassword(String testPassword) {
        return this.password.equals(testPassword);
    }

    public boolean isMatchingId(User user) {
        return this.id == user.id;
    }


    //수정필요
    public void update(User user) {
//        this.password(user.getPassword());
        this.setName(user.getName());
        this.setEmail(user.getEmail());
    }
}
