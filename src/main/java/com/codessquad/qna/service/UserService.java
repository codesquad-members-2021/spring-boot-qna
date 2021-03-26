package com.codessquad.qna.service;

import com.codessquad.qna.exception.UserAccountException;
import com.codessquad.qna.model.User;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.utils.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(User user) {
        if (userRepository.findByUserId(user.getUserId()).isPresent()) {
            throw new UserAccountException(ErrorMessage.DUPLICATED_ID);
        }
        userRepository.save(user);
    }

    public User login(String userId, String password) {
        return userRepository.findByUserIdAndPassword(userId, password).orElseThrow(
                () -> new UserAccountException(ErrorMessage.LOGIN_FAILED));
    }
}
