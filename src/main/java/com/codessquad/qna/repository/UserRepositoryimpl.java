package com.codessquad.qna.repository;

import com.codessquad.qna.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryimpl implements UserRepository {

    private List<User> users = new ArrayList<>();

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public Optional<User> findById(String userId) {

        return users.stream().filter(user -> user.getUserId().equals(userId)).findAny();
    }

    @Override
    public Optional<User> findByName(String name) {

        return users.stream().filter(user -> user.getName().equals(user.getName())).findAny();
    }

    @Override
    public List<User> findUserList() {

        return users;
    }

}
