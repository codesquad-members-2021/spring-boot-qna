package com.codessquad.qna.repository;

import com.codessquad.qna.model.User;

import java.util.ArrayList;
import java.util.List;

public class Users {

    private final List<User> userList = new ArrayList<>();

    public void addUser(User user) {
        userList.add(user);
    }

    public List<User> getAllUser() {
        return this.userList;
    }

    public User findUser(String userId) {
        return this.userList.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .get();
    }

    public void updateUser(String userId, User user) {
        User oldUser = findUser(userId);
        oldUser.setUserId(user.getUserId());
        oldUser.setPassword(user.getPassword());
        oldUser.setName(user.getName());
        oldUser.setEmail(user.getEmail());
    }

}
