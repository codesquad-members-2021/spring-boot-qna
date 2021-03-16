package com.codesquad.qna.service;

import com.codesquad.qna.domain.User;
import com.codesquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void update(User user, User updatedUser) {
        user.update(updatedUser);
        save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    public User findUserById(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(IllegalArgumentException::new);
    }
}
