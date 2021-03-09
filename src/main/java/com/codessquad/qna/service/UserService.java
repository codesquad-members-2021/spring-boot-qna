package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.valid.UserValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Long join(User user) {
        UserValidation.validUserInfo(user);
        User DBUser = userRepository.save(user);
        return DBUser.getId();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(NullPointerException::new);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public boolean update(User user, String newPassword) {
        UserValidation.validUserInfo(user);
        User getUser = findById(user.getId());
        if (getUser.checkPassword(user)) {
            getUser.updateUserInfo(user, newPassword);
            return true;
        }
        return false;
    }

}
