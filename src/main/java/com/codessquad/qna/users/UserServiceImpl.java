package com.codessquad.qna.users;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {
    private final List<User> users = Collections.synchronizedList(new ArrayList<>());

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
        }
        return;
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
