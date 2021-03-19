package com.codesquad.qna.service;

import com.codesquad.qna.domain.User;
import com.codesquad.qna.exception.IllegalUserAccessException;
import com.codesquad.qna.exception.UserNotFoundException;
import com.codesquad.qna.repository.UserRepository;
import com.codesquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void update(User user, User updatedUser, String newPassword) {
        if (newPassword.length() > 0) {
            updatedUser.setPassword(newPassword);
        }
        user.update(updatedUser);
        save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findUserByUserId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public User findUserByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public User findUserBySession(Long id, HttpSession session) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        logger.debug("User : {} found", sessionedUser.getUserId());

        if (!sessionedUser.isMatchedId(id)) {
            throw new IllegalUserAccessException();
        }

        User user = findUserByUserId(id);
        logger.debug("User : {} found", sessionedUser.getUserId());

        return user;
    }
}
