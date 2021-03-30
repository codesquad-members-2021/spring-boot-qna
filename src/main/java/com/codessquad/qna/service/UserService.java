package com.codessquad.qna.service;


import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.LoginFailedException;
import com.codessquad.qna.exception.NotFoundException;
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

    public List<User> findUsers() {

        return (List<User>)repository.findAll();
    }

    public User findUser(Long id) {

        return repository.findById(id).orElseThrow(() -> new NotFoundException());
    }

    public User findById(String userId) {

        User user = repository.findByUserId(userId).orElseThrow(() -> new LoginFailedException());

        return user;
    }


}
