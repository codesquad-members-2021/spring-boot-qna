package com.codessquad.qna.service;

import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
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
        User toUpdate = userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다"));
        if (toUpdate.verify(user)) {
            toUpdate.update(user);
            userRepository.save(toUpdate);
        }
    }

    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id + " 사용자를 찾을 수 없습니다"));
    }

    public User getUser(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다"));
    }
}
