package com.codessquad.qna.domain;

import com.codessquad.qna.utils.SessionUtil;

import javax.persistence.*;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Entity
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 20, unique = true)
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

    public Long getId() {
        return id;
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
        this.email = newUser.email;
        this.password = newUser.password;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Objects.equals(getUserId(), user.getUserId());
    }// 해당 equals 오버라이딩은 id가 동일한지만을 검사함
    // @Todo 삭제예정

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public boolean isMatchingPassword(String password) {
        return this.password.equals(password);
    }

    public boolean isSessionSameAsUser(HttpSession session) {
        return this.equals(SessionUtil.getLoginUser(session));
    }
}
