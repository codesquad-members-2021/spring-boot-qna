package com.codessquad.qna.entity;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
public class User {

    @Id
    @Column(name = "USER_PK")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String userId;

    private String password;
    private String name;
    private String email;

    @OneToMany(mappedBy = "author")
    private List<Post> posts;

    protected User() {
    }

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public void change(User updateUser) {
        this.userId = updateUser.userId;
        this.password = updateUser.password;
        this.name = updateUser.name;
        this.email = updateUser.email;
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

    public List<Post> getPosts() {
        return Collections.unmodifiableList(posts);
    }

    public boolean isMatchedPassword(String password) {
        return this.password.equals(password);
    }

    public boolean isMatchedId(Long id) {
        return this.getId().equals(id);
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
