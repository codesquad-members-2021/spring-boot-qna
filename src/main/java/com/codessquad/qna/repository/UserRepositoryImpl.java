package com.codessquad.qna.repository;

import com.codessquad.qna.entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class UserRepositoryImpl implements UserRepository{

    private final ConcurrentMap<String, User> users;

    public UserRepositoryImpl(ConcurrentMap<String, User> users) {
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
    public void remove(String userId) {
        users.remove(userId);
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public int size() {
        return users.size();
    }

}
