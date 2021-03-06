package com.codessquad.qna.repository;

import com.codessquad.qna.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Users {

    private final List<User> userList = new ArrayList<>();

    public boolean addUser(User user) {
        int index = getUserIndex(user.getUserId());
        if (index == -1) {
            userList.add(user);
            return true;
        }
        return false;
    }

    public List<User> getAllUser() {
        return this.userList;
    }

    public User findUser(String userId) {
        int index = getUserIndex(userId);
        if (index != -1) {
            return this.userList.get(index);
        }
        return new User();
    }

    private int getUserIndex(String userId) {
        return IntStream.range(0, this.userList.size())
                .filter(i -> this.userList.get(i).getUserId().equals(userId))
                .findFirst()
                .orElse(-1);
    }

    public boolean updateUser(String userId, User user, String newPassword) {
        User oldUser = findUser(userId);
        if (oldUser.getUserId() != null && user.getPassword().equals(oldUser.getPassword())) {
            oldUser.setUserId(user.getUserId());
            oldUser.setPassword(newPassword);
            oldUser.setName(user.getName());
            oldUser.setEmail(user.getEmail());
            return true;
        }
        return false;
    }

}
