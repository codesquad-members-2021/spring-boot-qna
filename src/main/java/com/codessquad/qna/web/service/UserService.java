package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.domain.repository.UserRepository;
import com.codessquad.qna.web.exception.UnAuthenticatedLoginException;
import com.codessquad.qna.web.exception.UnauthorizedUserException;
import com.codessquad.qna.web.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    private UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void verifySessionUser(User sessionedUser, User user, String errorMessage) {
        if (!sessionedUser.equals(user)) {
            throw new UnauthorizedUserException(errorMessage);
        }
    }

    public User findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UnauthorizedUserException(UserNotFoundException.USER_NOT_FOUND));
        return user;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public boolean isCorrectPassword(User user, User newInfoUser) {
        return user.hasSamePassword(newInfoUser);
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new UnauthorizedUserException(UserNotFoundException.USER_NOT_FOUND));
    }

    public User verifyUser(String userId, String password) {
        User user = findByUserId(userId);
        if (!user.matchesPassword(password)) {
            throw new UnAuthenticatedLoginException();
        }
        return user;
    }

    public void update(User user, User newInfoUser) {
        user.update(newInfoUser);
        save(user);
    }
}
