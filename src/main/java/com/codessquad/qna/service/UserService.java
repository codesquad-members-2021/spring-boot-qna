package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.util.HttpSessionUtils;
import com.codessquad.qna.valid.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Long join(User user) {
        UserValidator.validate(user);
        return userRepository.save(user).getId();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(NullPointerException::new);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public boolean update(User updatedUser, String newPassword, Long id) {
        UserValidator.validate(updatedUser);
        User user = findById(id);
        if (user.checkPassword(updatedUser.getPassword())) {
            user.updateUserInfo(updatedUser, newPassword);
            return true;
        }
        return false;
    }

    public void checkLoginable(String userId, String password) {
        User findUser = findByUserId(userId);
        if (!findUser.checkPassword(password)) {
            throw new IllegalArgumentException("비밀번호가 다름");
        }
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(NullPointerException::new);
    }

    public void checkSameUser(User user, Long id) {
        if (!user.checkId(id)) {
            throw new IllegalUserAccessException("자신의 정보만 수정 가능");
        }
    }

}
