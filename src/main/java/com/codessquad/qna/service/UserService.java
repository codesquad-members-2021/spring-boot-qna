package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepositoryimpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepositoryimpl repository;

    public UserService(UserRepositoryimpl repository) {
        this.repository = repository;
    }

    public void create(User user){
        repository.save(user);
    }

    public List<User> findUsers(){

        return repository.findUserList();
    }

    public Optional<User> findUser(String userId) {

        return repository.findById(userId);
    }

    public boolean validationUserInfo(String userIdCheck, String password) {

        return Optional.ofNullable(repository.findById(userIdCheck).get().getPassword().equals(password)).isPresent();
    }


}
