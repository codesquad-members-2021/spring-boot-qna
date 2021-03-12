package com.codessquad.qna.service;

import com.codessquad.qna.model.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean save(User user) {
        User duplicateUser = findByUserId(user.getUserId());
        if (!duplicateUser.nonNull()) {
            this.userRepository.save(user);
            return true;
        }
        return false;
    }

    public User login(String userId, String password) {
        User targetUser = findByUserId(userId);
        if (targetUser.nonNull() && targetUser.matchPassword(password)) {
            return targetUser;
        }
        return new User();
    }

    public boolean update(Long id, User user, String oldPassword, User sessionUser) {
        User loginUser = verifyUser(id, sessionUser);
        if (loginUser.nonNull() && loginUser.matchPassword(oldPassword)) {
            loginUser.update(user);
            this.userRepository.save(loginUser);
            return true;
        }
        return false;
    }

    public User verifyUser(Long id, User sessionUser) {
        if (sessionUser.matchId(id)) {
            return sessionUser;
        }
        return new User();
    }

    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        this.userRepository.findAll().forEach(userList::add);
        return userList;
    }

    public User findByUserId(String userId) {
        Optional<User> user = this.userRepository.findByUserId(userId);
        return user.orElseGet(User::new);
    }

}
