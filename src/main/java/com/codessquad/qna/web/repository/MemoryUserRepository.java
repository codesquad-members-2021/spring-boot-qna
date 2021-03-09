package com.codessquad.qna.web.repository;

import com.codessquad.qna.web.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryUserRepository {
    private List<User> users = Collections.synchronizedList(new ArrayList<>());

    public void save(User user) {
        users.add(user);
    }

    public Optional<User> findByUserId(String userId) {
        return users.stream()
                .filter(user -> user.getUserId()
                        .equals(userId)).findAny();
    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    public void update(User originUser, User user) {
        users.set(users.indexOf(originUser), user);
    }
}
