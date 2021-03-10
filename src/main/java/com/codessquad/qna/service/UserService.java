package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public void addUser(User user) {
        user.setId(userRepository.getUsers().size() + 1);
        userRepository.addUser(user);
    }

    public User findUserById(String userId) {
        for (User user : userRepository.getUsers()) {
            if (user.matchUserId(userId)) {
                return user;
            }
        }
        return null;
    }
}
