package com.codessquad.qna.entity;

import com.codessquad.qna.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString
@NoArgsConstructor
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

    public void change(String id, String password, String name, String email) {
        this.userId = id;
        this.password = password;
        this.name = name;
        this.email = email;
    }

}
