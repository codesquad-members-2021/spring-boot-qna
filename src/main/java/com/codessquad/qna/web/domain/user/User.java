package com.codessquad.qna.web.domain.user;

import com.codessquad.qna.web.domain.AbstractEntity;
import com.codessquad.qna.web.domain.answer.Answer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class User extends AbstractEntity {

    @Column(nullable = false, unique = true, length = 20)
    @JsonProperty
    private String userId;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    @JsonProperty
    private String name;

    @Column(nullable = false)
    @JsonProperty
    private String email;

    @OneToMany(mappedBy = "writer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Answer> answers = new ArrayList<>();

    public User(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
        if (answer.getWriter() != this) {
            answer.setWriter(this);
        }
    }

    public boolean isMatchingPassword(String password) {
        return this.password.equals(password);
    }

    public boolean isMatchingWriter(User user) {
        return this.equals(user);
    }

    public void update(User user) {
        this.password = user.password;
        this.name = user.name;
        this.email = user.email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) && Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), userId);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public static Builder build() {
        return new Builder();
    }

    public static Builder build(String userId, String password) {
        return new Builder(userId, password);
    }


    static public class Builder {
        private String userId;
        private String password;
        private String name = "unknown";
        private String email = "unknown";

        private Builder() {
        }

        private Builder(String userId, String password) {
            this.userId = userId;
            this.password = password;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(userId, password, name, email);
        }

    }

}
