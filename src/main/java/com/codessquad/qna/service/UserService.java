package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import java.util.List;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user) {
        userRepository.save(user);
    }

    public List<User> findUserAll() {
        return userRepository.findUserALl();
    }

    public User findUserByUserId(String userId) {
        return userRepository.findUserByUserID(userId);
    }

    public boolean isMatchingPassword(String originPassword, String receivedPassword) {
        return originPassword.equals(receivedPassword);
    }

    public void updateUserData(User user) {
        userRepository.updateUserData(user);
    }
}
