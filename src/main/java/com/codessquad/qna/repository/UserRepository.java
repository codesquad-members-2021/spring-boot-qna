package com.codessquad.qna.repository;

import com.codessquad.qna.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class UserRepository {

    private List<User> users = new ArrayList<>();

    public String save(User user) {
        users.add(user);
        return user.getUserId();
    }

    public List<User> findAll() {
        return Collections.unmodifiableList(users);
    }

    public User findById(String userId) {
        return users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findAny()
                .orElse(null);
    }

    public boolean checkPassword(User user) {
        User getUser = findById(user.getUserId());

        return getUser.getPassword().equals(user.getPassword());
    }

    public String updateUserInfo(User user, String newPassword) {
        User getUser = findById(user.getUserId());
        getUser.setName(user.getName());
        getUser.setPassword(newPassword);
        getUser.setEmail(user.getEmail());

        return getUser.getUserId();
    }
}
