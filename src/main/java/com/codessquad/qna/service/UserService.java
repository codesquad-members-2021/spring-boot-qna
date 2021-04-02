package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import com.codessquad.qna.exception.UnauthenticatedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void create(User user) {
        userRepository.save(user);
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public User findUserByUserId(String userId) {
        return userRepository.findUserByUserId(userId).orElseThrow(() -> new UnauthenticatedException("로그인하실 수 없습니다."));
    }

    public boolean isNewUser(User user) {
        return !userRepository.findUserByUserId(user.getUserId()).isPresent();
    }
}
