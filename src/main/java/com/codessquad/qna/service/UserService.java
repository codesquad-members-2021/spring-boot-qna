package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NoUserException;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(NoUserException::new);
    }

    public User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public void updateUser(long id, User updateUser, String newPassword) {
        User user = getUserById(id);
        checkPassword(user, updateUser);

        user.update(updateUser, newPassword);

        save(user);
    }

    private void checkPassword(User originalUser, User updateUser) {
        if (!originalUser.isMatchingPassword(updateUser.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 맞지않습니다. 비밀번호를 확인해 주세요");
        }
    }

}
