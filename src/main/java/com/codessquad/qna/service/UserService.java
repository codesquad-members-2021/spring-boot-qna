package com.codessquad.qna.service;

import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.domain.user.UserRepository;
import com.codessquad.qna.exception.UserNotFoundException;
import com.codessquad.qna.exception.WrongPasswordException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Long create(User user) {
        return userRepository.save(user).getId();
    }

    @Transactional
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Transactional
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    @Transactional
    public Long update(Long id, User userWithUpdatedInfo) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.isCorrectPassword(userWithUpdatedInfo.getPassword());
        user.update(userWithUpdatedInfo);
        return id;
    }
}
