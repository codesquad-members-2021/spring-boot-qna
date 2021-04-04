package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.repository.UserRepository;
import com.codessquad.qna.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void update(User user, User updatedUser) {
        userRepository.save(user.updateProfile(updatedUser));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
    }

    public boolean isUserIdPresent(String userId) {
        return userRepository.findByUserId(userId).isPresent();
    }
}
