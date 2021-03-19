package com.codesquad.qna.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32, unique = true)
    @NotBlank(message = "Name may not be blank")
    private String userId;

    @Column(nullable = false, length = 64)
    @NotBlank(message = "Password may not be blank")
    private String password;

    @Column(length = 32)
    private String name;

    @Column(length = 64)
    private String email;

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

    public void setId(long id) {
        this.id = id;
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

    public void update(User user) {
        this.userId = user.userId;
        this.password = user.password;
        this.name = user.name;
        this.email = user.email;
    }

    public boolean isMatchedId(Long id) {
        return this.id.equals(id);
    }

    public boolean isMatchedUserId(String userId) {
        return this.userId.equals(userId);
    }

    public boolean isMatchedPassword(User user) {
        return this.password.equals(user.password);
    }

    public boolean isMatchedPassword(String password) {
        return this.password.equals(password);
    }
}
