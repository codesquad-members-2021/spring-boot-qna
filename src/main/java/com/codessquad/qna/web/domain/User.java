package com.codessquad.qna.web.domain;

import com.codessquad.qna.web.exception.UnauthorizedUserException;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "아이디는 필수 입력값입니다.")
    private String userId;

    @Column(nullable = false)
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    @Column(nullable = false)
    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    private String email;

    protected User() {}

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

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public void update(User newInfoUser) {
        this.userId = newInfoUser.userId;
        this.name = newInfoUser.name;
        this.email = newInfoUser.email;
    }

    public boolean matchesPassword(String inputPassword) {
        return password.equals(inputPassword);
    }

    public boolean hasSamePassword(User user) {
        return this.password.equals(user.password);
    }

    public void verifySessionUser(User sessionedUser, String errorMessage) {
        if (!this.equals(sessionedUser)) {
            throw new UnauthorizedUserException(errorMessage);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId.equals(user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
