package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exception.IllegalEntityIdException;
import com.codessquad.qna.web.repository.UserRepository;
import org.springframework.stereotype.Service;

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
                .orElseThrow(IllegalEntityIdException::new);
    }

    private User findUser(String userId) {
        return userRepository
                .findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("찾는 회원이 없습니다"));
    }

    public void updateUser(String testPassword, User loginUser, User user) {
        validatePassword(loginUser, testPassword);
        loginUser.update(user);
        userRepository.save(loginUser);
    }

    private void validatePassword(User user, String testPassword) {
        if (!user.isMatchingPassword(testPassword)) {
            throw new IllegalStateException("잘못된 비밀번호 입니다");
        }
    }
}
