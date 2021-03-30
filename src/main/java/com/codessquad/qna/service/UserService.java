package com.codessquad.qna.service;

import com.codessquad.qna.exception.EntityNotFoundException;
import com.codessquad.qna.exception.InvalidSessionException;
import com.codessquad.qna.exception.UserAccountException;
import com.codessquad.qna.model.User;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.utils.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void update(Long id, User targetUser, String currentPassword, User sessionedUser) {
        User user = verifyUser(id, sessionedUser);
        if (!user.matchPassword(currentPassword)) {
            throw new UserAccountException(ErrorMessage.WRONG_PASSWORD);
        }
        user.update(targetUser);
        userRepository.save(user);
    }

    public User verifyUser(Long id, User sessionedUser) {
        if (!sessionedUser.matchId(id)) {
            throw new InvalidSessionException();
        }
        return sessionedUser;
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
