package com.codessquad.qna.users;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final List<User> users = Collections.synchronizedList(new ArrayList<>());

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User newUser) {
        for (User user : users) {
            if (user.getUserId().equals(newUser.getUserId())) {
                return;
            }
        }

        users.add(newUser);
    }

    public void updateUser(User toUpdate) {
        for (User user : users) {
            if (user.equals(toUpdate)) {
                user.update(toUpdate);
                return;
            }
        }
    }

    public Optional<User> getUser(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}
