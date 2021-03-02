package com.codessquad.qna.repository;

import com.codessquad.qna.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    List<User> userStorage = new ArrayList<>();

    public void save(User user){
        userStorage.add(user);
    }
}
