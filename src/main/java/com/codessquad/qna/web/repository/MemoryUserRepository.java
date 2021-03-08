package com.codessquad.qna.web.repository;

import com.codessquad.qna.web.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class MemoryUserRepository implements UserRepository {
    private List<User> users = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return users.stream()
                .filter(user -> user.getUserId()
                        .equals(userId)).findAny();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public void update(User originUser, User user) {
        users.set(users.indexOf(originUser), user);
    }
}
