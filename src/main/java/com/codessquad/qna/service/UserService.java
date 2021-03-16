package com.codessquad.qna.service;

import com.codessquad.qna.exception.*;
import com.codessquad.qna.model.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        if (this.userRepository.findByUserId(user.getUserId()).isPresent()) {
            throw new DuplicateUserIdFoundException();
        }
        this.userRepository.save(user);
    }

    public User login(String userId, String password) {
        User targetUser = this.userRepository.findByUserId(userId).orElseThrow(IdOrPasswordNotMatchException::new);
        if (!targetUser.matchPassword(password)) {
            throw new IdOrPasswordNotMatchException();
        }
        return targetUser;
    }

    public void update(Long id, User user, String oldPassword, User sessionUser) {
        User loginUser = verifyUser(id, sessionUser);
        if (!loginUser.matchPassword(oldPassword)) {
            throw new CurrentPasswordNotMatchException();
        }
        loginUser.update(user);
        this.userRepository.save(loginUser);
    }

    public User verifyUser(Long id, User sessionUser) {
        if (!sessionUser.matchId(id)) {
            throw new IllegalUserAccessException();
        }
        return sessionUser;
    }

    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        this.userRepository.findAll().forEach(userList::add);
        return userList;
    }

    public User findByUserId(String userId) {
        return this.userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
    }

}
