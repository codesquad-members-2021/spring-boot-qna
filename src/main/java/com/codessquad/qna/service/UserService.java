package com.codessquad.qna.service;

import com.codessquad.qna.controller.UserController;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.UserExistException;
import com.codessquad.qna.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User join(User newUser) {
        checkRedundancy(newUser);

        return userRepository.save(newUser);
    }

    private void checkRedundancy(User user) {
        userRepository.findByUserId(user.getUserId())
                .ifPresent(u -> {
                    throw new UserExistException();
                });
    }

    public Iterable<User> showAll() {
        return userRepository.findAll();
    }

    public Optional<User> showOneById(Long id) {
        return userRepository.findById(id);
    }

    public void updateInfo(User presentUser, User referenceUser) {

        referenceUser.setUserId(presentUser.getUserId());

        userRepository.delete(presentUser);
        userRepository.save(referenceUser);
    }
}

