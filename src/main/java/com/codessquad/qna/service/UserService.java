package com.codessquad.qna.service;

import com.codessquad.qna.exception.*;
import com.codessquad.qna.model.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        if (this.userRepository.findByUserId(user.getUserId()).isPresent()) {
            throw new UserAccountException("이미 사용 중인 아이디입니다.");
        }
        this.userRepository.save(user);
    }

    public User login(String userId, String password) {
        return this.userRepository.findByUserIdAndPassword(userId, password).orElseThrow(() ->
                new UserAccountException("아이디 또는 비밀번호가 일치하지 않습니다."));
    }

    public void update(Long id, User user, String oldPassword, User sessionUser) {
        User loginUser = verifyUser(id, sessionUser);
        if (!loginUser.matchPassword(oldPassword)) {
            throw new UserAccountException("기존 비밀번호가 일치하지 않습니다.");
        }
        loginUser.update(user);
        this.userRepository.save(loginUser);
    }

    public User verifyUser(Long id, User sessionUser) {
        if (!sessionUser.matchId(id)) {
            throw new UserSessionException();
        }
        return sessionUser;
    }

    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        this.userRepository.findAll().forEach(userList::add);
        return userList;
    }

    public User findByUserId(String userId) {
        return this.userRepository.findByUserId(userId).orElseThrow(NotFoundException::new);
    }

}
