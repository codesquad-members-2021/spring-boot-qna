package com.codessquad.qna.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, length = 20, unique = true)
    private String userId;
    @Column(nullable = false, length = 20)
    private String password;
    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false, length = 40, unique = true)
    private String email;

    public User() {
    }

    public User(Long id, String userId, String password, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static User of(Long id, User user) {
        return new User(id, user.userId, user.password, user.name, user.email);
    }

    public static List<User> getDummyData() {
        return Arrays.asList(
                new User(null, "javajigi", "1234", "자바지기", "javajigi@sample.net"),
                new User(null, "slipp", "1234", "슬립", "slipp@sample.net")
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
