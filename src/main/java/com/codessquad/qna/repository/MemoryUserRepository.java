package com.codessquad.qna.repository;


import com.codessquad.qna.domain.User;
import java.util.ArrayList;
import java.util.List;

public class MemoryUserRepository implements UserRepository{

    private List<User> users = new ArrayList<>();


    @Override
    public void save(User user) {
        users.add(user);
    }
}
