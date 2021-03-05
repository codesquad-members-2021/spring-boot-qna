package com.codessquad.qna.web.domain;

public class User {
    private int index;
    private String userId;
    private String password;
    private String name;
    private String email;

    User() {
    }

    public void setIndex(int index) {
        this.index = index;
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

    public int getIndex() {
        return index;
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
}
