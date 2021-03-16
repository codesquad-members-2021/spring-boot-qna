package com.codessquad.qna.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public void addUser(User newUser) {
        Optional<User> existUser = userRepository.findByUserId(newUser.getUserId());
        if (existUser.isPresent()) {
            return;
        }
        userRepository.save(newUser);
    }

    public void updateUser(User user) {
        Optional<User> toUpdate = userRepository.findByUserId(user.getUserId());
        if (toUpdate.isPresent()) {
            toUpdate.get().update(user);
            userRepository.save(toUpdate.get());
        }
        return;
    }

    public Optional<User> getUser(String userId) {
        return userRepository.findByUserId(userId);
    }
}
