package com.codessquad.qna.repository;

import com.codessquad.qna.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Users {

    private final List<User> userList = new ArrayList<>();

    public void addUser(User user) {
        userList.add(user);
    }

    public List<User> getAllUser() {
        return this.userList;
    }

    public User findUser(String userId) {
        int index = getUserIndex(userId);
        return this.userList.get(index);
    }

    public void deleteUser(String userId) {
        int index = getUserIndex(userId);
        this.userList.remove(index);
    }

    private int getUserIndex(String userId) {
        int index = IntStream.range(0, this.userList.size())
                .filter(i -> this.userList.get(i).getUserId().equals(userId))
                .findFirst()
                .orElse(-1);
        if (index == -1) {
            throw new IllegalStateException("해당 유저가 없습니다.");
        }
        return index;
    }

    public void updateUser(String userId, User user) {
        User oldUser = findUser(userId);
        oldUser.setUserId(user.getUserId());
        oldUser.setPassword(user.getPassword());
        oldUser.setName(user.getName());
        oldUser.setEmail(user.getEmail());
    }

}
