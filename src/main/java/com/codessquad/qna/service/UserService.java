package com.codessquad.qna.service;

import com.codessquad.qna.HttpSessionUtils;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

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

    public User findById(String userId){

        User user = repository.findByUserId(userId);

        return user;
    }


}
