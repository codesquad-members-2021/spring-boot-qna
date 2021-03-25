package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    @JsonProperty
    private String userId;

    @Column(nullable = false, length = 20)
    @JsonIgnore
    private String password;

    @Column(nullable = false, length = 20)
    @JsonProperty
    private String name;

    @Column(nullable = false, length = 50)
    @JsonProperty
    private String email;

    protected User() {

    }

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
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

    public void update(User user) {
        this.password = user.password;
        this.name = user.name;
        this.email = user.email;
    }

    public boolean verify(User toVerify) {
        return this.userId.equals(toVerify.userId) && this.password.equals(toVerify.password);
    }

    public boolean isSameId(User user) {
        return this.id.equals(user.id);
    }
}
