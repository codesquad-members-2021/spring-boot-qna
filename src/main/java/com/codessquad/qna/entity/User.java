package com.codessquad.qna.entity;

import com.codessquad.qna.dto.UserDto;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@ToString
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

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public void change(String id, String password, String name, String email) {
        this.userId = id;
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

    public boolean isMatchedPassword(String password) {
        return this.password.equals(password);
    }

    public boolean isMatchedId(Long id) {return this.getId().equals(id);}

}
