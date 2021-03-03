package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepostiory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
public class UserService {

    private final UserRepostiory userRepostiory;

    public UserService(UserRepostiory userRepostiory) {
        this.userRepostiory = userRepostiory;
    }

    public void join(User user) {
        userRepostiory.save(user);
    }

    public List<User> findUsers() {
        return userRepostiory.findAll();
    }

    public Optional<User> findUser(Long userId) {
        return userRepostiory.findById(userId);
    }
}
