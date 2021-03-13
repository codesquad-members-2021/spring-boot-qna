package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void create(User user){
        repository.save(user);
    }

    public List<User> findUsers(){

        return (List<User>)repository.findAll();
    }

    public Optional<User> findUser(Long id) {

        return repository.findById(id);
    }

    public boolean validationUserInfo(Long id, String password) {

        User user = repository.findById(id).get();

        return Optional.of(user.matchPassword(password)).get();
    }


}
