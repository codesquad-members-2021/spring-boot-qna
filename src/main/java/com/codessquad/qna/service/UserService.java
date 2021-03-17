package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import com.codessquad.qna.exception.LoginFailedException;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.exception.UnauthorizedAccessException;
import com.codessquad.qna.exception.UserExistException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getList() {
        return userRepository.findAll();
    }

    public void register(User user) {
        userRepository.findByUserId(user.getUserId())
                .ifPresent(u -> {
                    throw new UserExistException();
                });
        userRepository.save(user);
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void update(Long id, String oldPassword, User newUserInfo) {
        User user = userRepository.findById(id)
                .filter(u -> u.matchesPassword(oldPassword))
                .orElseThrow(() -> new UnauthorizedAccessException("권한이 존재하지 않습니다."));
        user.update(newUserInfo);
        userRepository.save(user);
    }

    public User authenticate(String userId, String password) {
        return userRepository.findByUserId(userId)
                .filter(u -> u.matchesPassword(password))
                .orElseThrow(LoginFailedException::new);
    }

    public void checkAccessId(User loginUser, Long accessId) {
        if (!loginUser.matchesId(accessId)) {
            throw new UnauthorizedAccessException("다른 사람의 정보를 수정할 수 없습니다.");
        }
    }

}
