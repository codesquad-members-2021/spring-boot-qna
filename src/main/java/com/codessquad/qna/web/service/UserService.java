package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.domain.repository.UserRepository;
import com.codessquad.qna.web.exception.InvalidUserException;
import com.codessquad.qna.web.exception.UnAuthenticatedLoginException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    private UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new InvalidUserException("해당 id의 사용자가 존재하지 않습니다."));
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
        return userRepository.findByUserId(userId).orElseThrow(() ->new InvalidUserException("사용자가 존재하지 않습니다."));
    }

    public User verifyUser(String userId, String password) {
        User user = findByUserId(userId);
        if (!user.matchesPassword(password)) {
            throw new UnAuthenticatedLoginException();
        }
        return user;
    }
}
