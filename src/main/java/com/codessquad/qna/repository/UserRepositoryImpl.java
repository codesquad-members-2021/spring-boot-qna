package com.codessquad.qna.repository;

import com.codessquad.qna.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private final ConcurrentMap<String, User> users = new ConcurrentHashMap<>();

    @Override
    public void save(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public Optional<User> getUser(String userId) {
        Optional<User> user = Optional.ofNullable(users.get(userId));
        return user;
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
