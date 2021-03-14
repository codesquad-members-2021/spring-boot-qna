package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import com.codessquad.qna.exception.LoginFailedException;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.exception.UnauthorizedAccessException;
import com.codessquad.qna.exception.UserExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public void register(User user) {
        userRepository.findByUserId(user.getUserId())
                .ifPresent(u -> {throw new UserExistException();});
        userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public void updateUserInfo(Long id, String oldPassword, User newUserInfo) {
        User user = userRepository.findById(id)
                .filter(u -> u.isMatchingPassword(oldPassword))
                .orElseThrow(() -> new UnauthorizedAccessException("권한이 존재하지 않습니다."));
        user.update(newUserInfo);
        userRepository.save(user);
    }

    @Override
    public User authenticate(String userId, String password) {
        return userRepository.findByUserId(userId)
                .filter(u -> u.isMatchingPassword(password))
                .orElseThrow(LoginFailedException::new);
    }

}
