package com.codessquad.qna.user;

public class User {
    private String userId;
    private String password;
    private String name;
    private String email;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    @Override
    public String toString(){
        return "userId = "+userId+" password = "+password+" name = "+name+" email = "+email;
    }
}
