package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

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
    void createUser() {
        User user = getUser();

        Long userId = userService.join(user);

        User findUser = userRepository.findById(userId).orElseThrow(NullPointerException::new);

        assertThat(findUser.getUserId()).isEqualTo("userId");
        assertThat(findUser.getPassword()).isEqualTo("password");

    }

    @Test
    @DisplayName("user 정보 update하기")
    void updateUser() {
        User user = getUser();
        User updateUser = getUser();
        updateUser.setEmail("newEmail");
        updateUser.setId(1L);

        Long userID = userService.join(user);
        userService.update(updateUser, "newPassword", 1L);
        User findUser = userRepository.findById(userID).orElseThrow(NullPointerException::new);

        assertThat(findUser.getPassword()).isEqualTo("newPassword");
        assertThat(findUser.getId()).isEqualTo(1L);
        assertThat(findUser.getEmail()).isEqualTo("newEmail");

    }


    private User getUser() {
        User user = new User(1L, "userA", "sss", "sss@sss");
        user.setUserId("userId");
        user.setPassword("password");
        user.setEmail("email");
        return user;
    }


}
