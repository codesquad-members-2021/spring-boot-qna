package com.codessquad.qna.repository;

import com.codessquad.qna.entity.User;

import java.util.Collections;
import java.util.List;

public class UserRepositoryList implements UserRepository{

    private final List<User> users;

    public UserRepositoryList(List<User> users) {
        this.users = users;
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public User getUser(int id) {
        return users.get(id);
    }

    @Override
    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

}
