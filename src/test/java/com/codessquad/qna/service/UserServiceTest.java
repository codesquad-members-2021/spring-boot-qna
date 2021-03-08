package com.codessquad.qna.service;

import com.codessquad.qna.MvcConfig;
import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.CanNotFindUserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

class UserServiceTest {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MvcConfig.class);
    UserService userService;

    @BeforeEach
    void rollback() {
        userService = applicationContext.getBean("userService", UserService.class);
    }

    @Test
    @DisplayName("유저가 정상적으로 저장되는지 테스트 한다.")
    void save() {
        User user = new User("roach", "1234", "roach", "dev0jsh@gmail.com");
        userService.save(user);
        assertThat(userService.getUser(1L).getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("저장된 모든 유저를 리턴하는지 테스트 한다.")
    void getUsers() {
        User user = new User("honux", "12345", "honux", "1234@naver.com");
        User user1 = new User("kyu", "123456", "kyu", "12345@naver.com");
        userService.save(user);
        userService.save(user1);
        Assertions.assertAll(
                ()->assertThat(userService.getUsers().get(0).getId()).isEqualTo(user.getId()),
                ()->assertThat(userService.getUsers().get(1).getId()).isEqualTo(user1.getId())
        );
    }

    @Test
    @DisplayName("유저가 정상적으로 불러와지는지 테스트 한다.")
    void getUser() {
        User user = new User("roach", "1234", "roach", "dev0jsh@gmail.com");
        User honux = new User("honux", "12345", "honux", "1234@naver.com");
        userService.save(user);
        userService.save(honux);
        assertThat(userService.getUser(1L).getId()).isEqualTo(user.getId());
    }

    @Test
    @DisplayName("유저가 없을시 CanNotFindUserException 을 리턴하는지 확인한다.")
    void failGetUser() {
        assertThatExceptionOfType(CanNotFindUserException.class)
                .isThrownBy(() -> userService.getUser(100L))
                .withMessage("해당 유저가 존재하지 않습니다.");
    }

    @Test
    void removeUser() {
        User user = new User("honux", "12345", "honux", "1234@naver.com");
        userService.save(user);
        userService.removeUser(1L);
        assertThatExceptionOfType(CanNotFindUserException.class)
                .isThrownBy(() -> userService.getUser(1L))
                .withMessage("해당 유저가 존재하지 않습니다.");
    }

}
