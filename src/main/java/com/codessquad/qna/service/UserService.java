package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
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
        UserValidator.validUserInfo(user);
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
        UserValidator.validUserInfo(user);
        User getUser = findById(user.getId());
        if (getUser.checkPassword(user.getPassword())) {
            getUser.updateUserInfo(user, newPassword);
            return true;
        }
        return false;
    }

    public boolean checkLoginable(String userId, String password) {
        User findUser = findByUserId(userId);
        return findUser != null && findUser.checkPassword(password);
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(NullPointerException::new);
    }

    public boolean checkSession(HttpSession session, Long id) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return false;
        }
        checkSameUser(session, id);
        return true;
    }

    private void checkSameUser(HttpSession session, Long id) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.checkId(id)) {
            throw new IllegalStateException("자신의 정보만 수정 가능");
        }
    }

}
