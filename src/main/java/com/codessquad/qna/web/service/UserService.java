package com.codessquad.qna.web.service;

import com.codessquad.qna.web.HttpSessionUtils;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(User user) {
        User originUser = findUser(user.getUserId());
        validatePassword(originUser, user.getPassword());
        return originUser;
    }

    public void signUp(User user) {
          userRepository.save(user);
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findUser(long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new IllegalStateException("찾는 회원이 없습니다"));
    }

    private User findUser(String userId) {
        return userRepository
                .findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("찾는 회원이 없습니다"));
    }

    public void updateUser(String testPassword, User originUser, User user) {
        validatePassword(originUser, testPassword);
        originUser.update(user);
        userRepository.save(originUser);
    }

    private void validatePassword(User originUser, String testPassword) {
        if (!originUser.isMatchingPassword(testPassword)) {
            throw new IllegalStateException("잘못된 비밀번호 입니다");
        }
    }
}
