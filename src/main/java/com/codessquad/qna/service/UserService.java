package com.codessquad.qna.service;

import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.CanNotFindUserException;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(CanNotFindUserException::new);
    }

    public User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(CanNotFindUserException::new);
    }

    public void change(User oldUser, User updateUserInfo) {
        oldUser.change(updateUserInfo.getUserId(), updateUserInfo.getPassword(), updateUserInfo.getName(), updateUserInfo.getEmail());
        userRepository.save(oldUser);
    }

    public void removeUser(Long id) {userRepository.deleteUserById(id);}

    public int countOfUsers() {return (int) userRepository.count();}

}
