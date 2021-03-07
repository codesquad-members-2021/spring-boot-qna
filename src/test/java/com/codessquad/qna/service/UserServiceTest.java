package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("user가 db에 잘 들어가는지 검사")
    void createUser(){
        User user = new User();
        user.setUserId("userId");
        user.setPassword("password");

        Long userId = userService.join(user);

        User findUser = userRepository.findById(userId).orElseThrow(NullPointerException::new);

        Assertions.assertThat(findUser.getUserId()).isEqualTo("userId");
        Assertions.assertThat(findUser.getPassword()).isEqualTo("password");

    }

}