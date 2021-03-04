package com.codessquad.qna.user;

public class User {

    private String userId;
    private String password;
    private String name;
    private String email;

    public User() {

    }

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

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

    public boolean isMatchingUserId(String userId) {
        return this.userId.equals(userId);
    }

    public boolean isMatchingPassword(String password) {
        return this.password.equals(password);
    }

    public void update(User newUserInfo) {
        this.password = newUserInfo.password;
        this.name = newUserInfo.name;
        this.email = newUserInfo.email;
    }
}
