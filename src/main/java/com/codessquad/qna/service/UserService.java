package com.codessquad.qna.service;

import com.codessquad.qna.controller.UserController;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void create(User user) {
        logger.info("users {}.", user);
        userRepository.save(user);
    }

    public void update(Long id, String password, String name, String email) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("null 값입니다."));
        user.updateUserInfo(password, name, email);
        logger.info("users {}.", user);
        userRepository.save(user);
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public User findById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new Exception("null 값입니다."));
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

}
