package com.codessquad.qna.domain;

public class User {

    private String userId;
    private String password;
    private String name;
    private String email;

    public User() {

    }

    public User(User copyUser) {
        this.userId = copyUser.userId;
        this.password = copyUser.password;
        this.name = copyUser.name;
        this.email = copyUser.email;
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


    public void update(User newUser) {
        this.name = newUser.name;
        this.userId = newUser.userId;
        this.email = newUser.email;
    }


}
