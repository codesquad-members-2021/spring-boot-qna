package com.codessquad.qna.service;

import com.codessquad.qna.model.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean save(User user) {
        Optional<User> duplicateUser = userRepository.findByUserId(user.getUserId());
        if (!duplicateUser.isPresent()) {
            this.userRepository.save(user);
            return true;
        }
        return false;
    }

    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        this.userRepository.findAll().forEach(userList::add);
        return userList;
    }

    public User findById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseGet(User::new);
    }

    public boolean update(Long id, User user, String newPassword) {
        AtomicBoolean isUpdate = new AtomicBoolean(false);
        Optional<User> optionalUser = this.userRepository.findById(id);
        optionalUser.ifPresent(targetUser -> {
            if (targetUser.getPassword().equals(user.getPassword())) {
                targetUser.setUserId(user.getUserId());
                targetUser.setPassword(newPassword);
                targetUser.setName(user.getName());
                targetUser.setEmail(user.getEmail());
                this.userRepository.save(targetUser);
                isUpdate.set(true);
            }
        });
        return isUpdate.get();
    }

}
