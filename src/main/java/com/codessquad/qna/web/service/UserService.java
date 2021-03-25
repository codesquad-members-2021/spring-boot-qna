package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exception.IllegalAccessException;
import com.codessquad.qna.web.exception.IllegalEntityIdException;
import com.codessquad.qna.web.exception.LoginFailException;
import com.codessquad.qna.web.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String userId, String password) {
        User user = findUser(userId);
        if (!isMatchingPassword(user, password)) {
            throw new LoginFailException("잘못된 비밀번호 입니다");
        }
        return user;
    }

    public void signUp(User user) {
        userRepository.save(user);
    }

    public boolean checkDuplicateID(User user) {
        return userRepository.findByUserId(user.getUserId()).isPresent();
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findUser(long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new IllegalEntityIdException("id(번호)에 해당하는 회원이 없습니다"));
    }

    private User findUser(String userId) {
        return userRepository
                .findByUserId(userId)
                .orElseThrow(() -> new LoginFailException("입력하신 아이디에 해당하는 회원이 없습니다"));
    }

    public boolean isUpdatable(long id, String testPassword, User loginUser, User user) {
        checkSameUser(id, loginUser);
        if(!isMatchingPassword(loginUser, testPassword)) {
            return false;
        }
        loginUser.update(user);
        userRepository.save(loginUser);
        return true;
    }

    public boolean isMatchingPassword(User user, String testPassword) {
        return user.isMatchingPassword(testPassword);
    }

    public void checkSameUser(long id, User user) {
        if (!user.isSameId(id)) {
            throw new IllegalAccessException();
        }
    }
}
