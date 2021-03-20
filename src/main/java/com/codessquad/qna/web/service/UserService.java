package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exception.IllegalEntityIdException;
import com.codessquad.qna.web.exception.LoginFailException;
import com.codessquad.qna.web.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.security.auth.login.FailedLoginException;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String userId, String password) {
        User user = findUser(userId);
        validatePassword(user, password);
        return user;
    }

    public void signUp(User user) {
          userRepository.save(user);
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findUser(long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new IllegalEntityIdException("id(번호)에 해당하는 회원이 없습니다"));
    }

    private User findUser(String userId) {
        return userRepository
                .findByUserId(userId)
                .orElseThrow(() -> new LoginFailException("입력하신 아이디에 해당하는 회원이 없습니다"));
    }

    public void updateUser(String testPassword, User loginUser, User user) {
        validatePassword(loginUser, testPassword);
        loginUser.update(user);
        userRepository.save(loginUser);
    }

    private void validatePassword(User user, String testPassword) {
        if (!user.isMatchingPassword(testPassword)) {
            throw new LoginFailException("잘못된 비밀번호 입니다");
        }
    }
}
