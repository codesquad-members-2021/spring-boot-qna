package com.codessquad.qna.service;

import com.codessquad.qna.model.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean save(User user) {
        if (userRepository.findByUserId(user.getUserId()).isPresent()) {
            return false;
        }
        userRepository.save(user);
        return true;
    }
}
