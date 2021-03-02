package com.codessquad.qna.repository;

import com.codessquad.qna.entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserRepositoryImpl implements UserRepository{

    private final Map<String, User> users;

    public UserRepositoryImpl(Map<String, User> users) {
        this.users = users;
    }

    @Override
    public void save(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public User getUser(String userId) {
        return users.get(userId);
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

}
