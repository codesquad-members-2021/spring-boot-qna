package com.codessquad.qna.service;

import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.CanNotFindUserException;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public User getUser(String userId) {
        Optional<User> user = userRepository.getUser(userId);
        if(!user.isPresent()){
            throw new CanNotFindUserException();
        }
        return user.get();
    }

    public void removeUser(String userId) {userRepository.remove(userId);}

    public int countOfUsers() {return userRepository.size();}

}
