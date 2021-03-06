package com.codessquad.qna.repository;

import com.codessquad.qna.domain.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryimpl implements ListUserRepository {

    private List<User> users = new ArrayList<>();

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public User findById(String userId) {
        for (User user : users){
            if(user.getUserId().equals(userId)){
                return user;
            }
        }
        return null;
    }

    @Override
    public User findByName(String name) {
        for(User user : users){
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> findUserList() {
        return users;
    }

}
