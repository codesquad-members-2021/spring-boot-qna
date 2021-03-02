package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepostiory;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepostiory userRepostiory;

    public UserService(UserRepostiory userRepostiory) {
        this.userRepostiory = userRepostiory;
    }

    public void join(User user) {
        userRepostiory.save(user);
    }
}
