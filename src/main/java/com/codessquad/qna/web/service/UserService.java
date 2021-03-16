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

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(User user) {
        User originUser = findUser(user.getUserId());
        validatePassword(originUser, user.getPassword());
        return originUser;
    }

    public void signUp(User user) {
        validateUserID(user);
        userRepository.save(user);
    }

    private void validateUserID(User user) {
        userRepository.findByUserId(user.getUserId())
                .ifPresent(x -> {
                    throw new IllegalStateException("이미 존재하는 아이디입니다");
                });
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findUser(long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new IllegalStateException("찾는 user가 없습니다"));
    }

    private User findUser(String userId) {
        return userRepository
                .findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("찾는 user가 없습니다"));
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

    //메소드명 변경 예정
    public User getSessionedUser(long id, HttpSession session) {
        User originUser = findUser(id);
        if(!originUser.isMatchingId(HttpSessionUtils.getSessionedUser(session))) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다");
        }
        return originUser;
    }
}
