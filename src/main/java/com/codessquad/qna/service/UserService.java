package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepositoryimpl;

import java.util.List;

public class UserService {

    UserRepositoryimpl repository = new UserRepositoryimpl();

    public void create(User user){
        repository.save(user);
    }

    public List<User> findUsers(){
        return repository.findUserList();
    }

    public User findUser(String userId) {
        return repository.findById(userId);
    }

    public boolean validationUserInfo(String userIdCheck, String password) {
        return repository.findById(userIdCheck).getPassword().equals(password);
    }
    
}
