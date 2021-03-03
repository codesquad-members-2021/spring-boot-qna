package com.codessquad.qna.domain;

public class User {

    public static int serialCode = 1;
    private int serialNum;
    private String userId;
    private String password;
    private String name;
    private String email;

    public User() {
        serialNum = serialCode++;
    }

    public int getSerialNum() {
        return serialNum;
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
}
