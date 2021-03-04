package com.codessquad.qna.user;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final List<User> users = new ArrayList<>();

    public void add(User user) {
        users.add(user);
    }

    public List<User> getUserList() {
        return Collections.unmodifiableList(users);
    }

    public Optional<User> findUserById(String userId) {
        for (User user : users) {
            if (user.isMatchingUserId(userId)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public boolean checkAndUpdate(String userId, String oldPassword, User newUserInfo) {
        Optional<User> optionalUser = findUserById(userId);
        if (!optionalUser.isPresent()) {
            return false;
        }
        User user = optionalUser.get();
        if (!user.isMatchingPassword(oldPassword)) {
            return false;
        }
        user.update(newUserInfo);
        return true;
    }

}
