package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user) {
        validateUserDuplication(user);
        userRepository.save(user);
    }

    private void validateUserDuplication(User user) {
        if (userRepository.existsUserByUserId(user.getUserId())) {
            throw new ExistedUserException();
        }
    }

    public List<User> findUserAll() {
        return (List<User>) userRepository.findAll();
    }

    public void updateUserData(User originUser, User user) {
        String newPassword = user.getPassword().split(",")[1];
        user.setPassword(newPassword);
        originUser.update(user);
        userRepository.save(originUser);
    }

    public User findUserByUserId(String userId) {
        return userRepository.findUserByUserId(userId);
    }

    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    public boolean confirmPassword(User originUser, User user) {
        String receivedPassword = user.getPassword().split(",")[0];

        if (originUser.isMatchingPassword(receivedPassword)) {
            return true;
        }

        return false;
    }
}
