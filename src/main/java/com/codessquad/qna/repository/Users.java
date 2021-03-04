package com.codessquad.qna.repository;

import com.codessquad.qna.model.User;

import java.util.ArrayList;
import java.util.List;

public class Users {

    private final List<User> userList = new ArrayList<>();

    public void addUser(User user) {
        User newUser = new User();
        newUser.setUserId(user.getUserId());
        newUser.setPassword(user.getPassword());
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        userList.add(newUser);
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

}
