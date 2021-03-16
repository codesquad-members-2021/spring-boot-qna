package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User join(User newUser) {
        checkRedundancy(newUser);

        return userRepository.save(newUser);
    }

    private void checkRedundancy(User user) {
        User redundantUser = userRepository.findByUserId(user.getUserId()).orElse(null);
        if (redundantUser != null) {
            throw new IllegalStateException();
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getOneById(Long id) {
        return userRepository.findById(id);
    }

    public void updateInfo(User presentUser, User referenceUser) {

        referenceUser.setUserId(presentUser.getUserId());

        userRepository.delete(presentUser);
        userRepository.save(referenceUser);
    }
}

