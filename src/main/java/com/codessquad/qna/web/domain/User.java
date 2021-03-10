package com.codessquad.qna.web.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    //isMatching...으로 메서드명 변경
    public boolean matchPassword(User user) {
        return user.password.equals(this.password);
    }

    //지금 다 바꿔주고 있는데, 사실은 mail과 password만 바꾸지 (이거분리, 로그인 베서드 구현 안해서)
    public void update(User user) {
        this.setName(user.getName());
        this.setEmail(user.getEmail());
    }
}
