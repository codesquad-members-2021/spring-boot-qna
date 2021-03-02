package com.codessquad.qna.service;

import com.codessquad.qna.entity.User;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.repository.UserRepositoryList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository = new UserRepositoryList(new ArrayList<>());

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

}
