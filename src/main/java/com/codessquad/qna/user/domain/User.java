package com.codessquad.qna.user.domain;

import com.codessquad.qna.common.BaseTimeEntity;

import javax.persistence.*;

@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    protected User() {}

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

    public void update(User user) {
        this.password = user.password;
        this.name = user.name;
        this.email = user.email;
    }

    public boolean matchId(Long id) {
        return this.id.equals(id);
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }
}
