package com.codessquad.qna.domain;

import com.codessquad.qna.exception.IllegalUserAccessException;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class User extends AbstractEntity {

    @Column(nullable = false, length = 20, unique = true)
    private String userId;

    private String name;
    private String password;
    private String email;

    protected User() {
    }

    public User(Long id, String userId, String name, String email) {
        super.setId(id);
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void updateUserInfo(User user, String newPassword) {
        this.name = user.getName();
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.password = newPassword;
    }

    public void checkSameUser(Long newId) {
        if (getId() != newId) {
            throw new IllegalUserAccessException("자신의 정보만 수정 가능");
        }
    }

}
