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
        return userRepository.findAll();
    }

    public User getUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        user.orElseThrow(CanNotFindUserException::new);
        return user.get();
    }


    public void change(User oldUserInfo, User updateUserInfo) {
        userRepository.update(oldUserInfo, updateUserInfo);
    }

    public void removeUser(Long userId) {userRepository.remove(userId);}

    public int countOfUsers() {return userRepository.size();}

}
