package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
public class User extends AbstractEntity implements Serializable {
    @NotBlank(message = "아이디를 입력해주세요.")
    @Column(nullable = false, length = 20, unique = true)
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @JsonIgnore
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Column(nullable = false, unique = true)
    private String email;

    protected User() {
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

    public void update(User newUser) {
        userId = newUser.userId;
        password = newUser.password;
        name = newUser.name;
        email = newUser.email;
    }

    public static boolean isValidPassword(User user, String password) {
        return user.password.equals(password);
    }
}

