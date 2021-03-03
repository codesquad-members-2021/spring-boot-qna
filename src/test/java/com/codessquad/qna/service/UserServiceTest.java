package com.codessquad.qna.service;

import com.codessquad.qna.entity.User;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.repository.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

class UserServiceTest {

    UserService userService;

    @BeforeEach
    void rollback() {
        userService = new UserService();
    }

    @Test
    @DisplayName("유저가 정상적으로 저장되는지 테스트 한다.")
    void save() {
        User user = new User("roach", "1234", "roach", "dev0jsh@gmail.com");
        userService.save(user);
        assertThat(userService.getUser("roach").get()).isEqualTo(user);
    }

    @Test
    @DisplayName("저장된 모든 유저를 리턴하는지 테스트 한다.")
    void getUsers() {
        User user = new User("honux", "12345", "honux", "1234@naver.com");
        User user1 = new User("kyu", "123456", "kyu", "12345@naver.com");
        userService.save(user);
        userService.save(user1);
        Assertions.assertAll(
                ()->assertThat(userService.getUsers().get(0)).isEqualTo(user1),
                ()->assertThat(userService.getUsers().get(1)).isEqualTo(user)
        );
    }

    @Test
    @DisplayName("유저가 정상적으로 불러와지는지 테스트 한다.")
    void getUser() {
        User user = new User("roach", "1234", "roach", "dev0jsh@gmail.com");
        User honux = new User("honux", "12345", "honux", "1234@naver.com");
        userService.save(user);
        userService.save(honux);
        assertThat(userService.getUser("roach").get()).isEqualTo(user);
    }

    @Test
    void removeUser() {
        User user = new User("honux", "12345", "honux", "1234@naver.com");
        userService.save(user);
        userService.removeUser("honux");
        assertThat(userService.contOfUsers()).isEqualTo(0);
    }

}
