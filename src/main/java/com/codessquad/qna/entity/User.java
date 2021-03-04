package com.codessquad.qna.entity;

import com.codessquad.qna.dto.UserDto;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String userId;

    private String password;
    private String name;
    private String email;

    public User(String id, String password, String name, String email) {
        this.userId = id;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public User() { }

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

    public void Change(String id, String password, String name, String email) {
        this.userId = id;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static User map(UserDto userDto) {
        return new User(userDto.getUserId(), userDto.getPassword(), userDto.getPassword(), userDto.getEmail());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
