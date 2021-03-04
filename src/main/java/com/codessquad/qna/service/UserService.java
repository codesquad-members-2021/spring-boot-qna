package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
public class UserService {

    private final UserRepository userRepostiory;

    public UserService(UserRepository userRepostiory) {
        this.userRepostiory = userRepostiory;
    }

    public void join(User user) {

        userRepostiory.save(user);
    }

    public List<User> findUsers() {
        return userRepostiory.findAll();
    }

    public Optional<User> findUser(Long id) {
        return userRepostiory.findById(id);
    }

    public Optional<User> findByUserId(String userId) {
        return userRepostiory.findByUserId(userId);
    }



}
