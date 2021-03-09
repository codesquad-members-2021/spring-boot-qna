package com.codessquad.qna.service;

import com.codessquad.qna.model.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.codessquad.qna.controller.HttpSessionUtils.getUserFromSession;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean save(User user) {
        User duplicateUser = findByUserId(user.getUserId());
        if (!duplicateUser.notNull()) {
            this.userRepository.save(user);
            return true;
        }
        return false;
    }

    public User login(String userId, String password) {
        User targetUser = findByUserId(userId);
        if (targetUser.notNull() && targetUser.matchPassword(password)) {
            return targetUser;
        }
        return new User();
    }

    public User verifyUser(Long id, HttpSession session) {
        User loginUser = getUserFromSession(session);
        if (loginUser.matchId(id)) {
            return loginUser;
        }
        return new User();
    }

    public boolean update(Long id, User user, String oldPassword, HttpSession session) {
        User loginUser = verifyUser(id, session);
        if (loginUser.notNull() && loginUser.matchPassword(oldPassword)) {
            loginUser.update(user);
            this.userRepository.save(loginUser);
            return true;
        }
        return false;
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
