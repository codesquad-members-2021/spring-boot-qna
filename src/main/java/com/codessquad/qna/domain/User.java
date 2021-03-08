package com.codessquad.qna.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String userId;
    private String password;
    private String name;
    @Column(nullable = false, unique = true)
    private String email;

    public boolean matchPassword(String password) {
        if (password == null) {
            return false;
        }
        return password.equals(this.password);
    }

    public boolean matchId(Long id) {
        if (id == null) {
            return false;
        }
        return id.equals(this.id);
    }

    public void update(User newUser) {
        userId = newUser.userId;
        password = newUser.password;
        name = newUser.name;
        email = newUser.email;
    }
}

