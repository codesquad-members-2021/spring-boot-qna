package com.codessquad.qna.service;

import com.codessquad.qna.model.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean save(User user) {
        User duplicateUser = findByUserId(user.getUserId());
        if (duplicateUser.getId() == null) {
            this.userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean login(String userId, String password, HttpSession session) {
        User targetUser = findByUserId(userId);
        if (targetUser.getId() != null && targetUser.getPassword().equals(password)) {
            session.setAttribute("user", targetUser);
            return true;
        }
        return false;
    }

    public boolean update(Long id, User user, String oldPassword) {
        User targetUser = findById(id);
        if (targetUser.getPassword().equals(oldPassword)) {
            targetUser.update(user);
            this.userRepository.save(targetUser);
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

    public User findById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseGet(User::new);
    }

}
