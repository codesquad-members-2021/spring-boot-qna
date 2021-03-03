package com.codessquad.qna.service;

import com.codessquad.qna.entity.User;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.repository.UserRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    private final UserRepository userRepository = new UserRepositoryImpl(new ConcurrentHashMap<>());

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public User getUser(String userId) {
        return userRepository.getUser(userId);
    }

    public void removeUser(String userId) {userRepository.remove(userId);}

    public int contOfUsers() {return userRepository.size();}

}
