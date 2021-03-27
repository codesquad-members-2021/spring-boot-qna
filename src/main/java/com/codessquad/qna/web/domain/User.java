package com.codessquad.qna.web.domain;

import com.codessquad.qna.web.exceptions.InvalidEntityException;
import com.codessquad.qna.web.exceptions.auth.UnauthorizedAccessException;

import javax.persistence.*;

import static com.codessquad.qna.web.exceptions.InvalidEntityException.EMPTY_FIELD_IN_USER_ENTITY;
import static com.codessquad.qna.web.exceptions.auth.UnauthorizedAccessException.CANNOT_MODIFY_ANOTHER_USER;
import static com.codessquad.qna.web.exceptions.auth.UnauthorizedAccessException.PASSWORD_NOT_MATCHING;

@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    protected User() {
    }

    public void update(User newUserInfo) {
        password = newUserInfo.password;
        name = newUserInfo.name;
        email = newUserInfo.email;
    }

    public boolean isValid() {
        return (password != null && !password.isEmpty())
                && (name != null && !name.isEmpty())
                && (email != null && !email.isEmpty());
    }

    public void verifyUserEntityIsValid() {
        if (!isValid()) {
            throw new InvalidEntityException(EMPTY_FIELD_IN_USER_ENTITY);
        }
    }

    public boolean isMatchingPassword(String anotherPassword) {
        return password.equals(anotherPassword);
    }

    public void verifyPassword(String password) {
        if (!isMatchingPassword(password)) {
            throw new UnauthorizedAccessException(PASSWORD_NOT_MATCHING);
        }
    }

    public boolean isMatchingId(User anotherUser) {
        return id.equals(anotherUser.id);
    }

    public void verifyIsSameUser(User anotherUser) {
        if (!isMatchingId(anotherUser)) {
            throw new UnauthorizedAccessException(CANNOT_MODIFY_ANOTHER_USER);
        }
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
