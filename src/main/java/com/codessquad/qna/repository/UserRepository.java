package com.codessquad.qna.repository;

import com.codessquad.qna.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final List<User> userStorage = new ArrayList<>();

    public void save(User user) {
        userStorage.add(user);
    }

    public List<User> getAll() {
        return userStorage;
    }

    public User getOne(String targetId) {
        return userStorage.stream()
                .filter(user -> user.getUserId().equals(targetId))
                .findAny()
                .get();
    }

    public boolean isRedundant(String targetId) {
        return userStorage.stream()
                .anyMatch(user -> user.getUserId().equals(targetId));
    }
}
