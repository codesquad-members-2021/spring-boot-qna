package com.codessquad.qna.service;

import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.AlreadyExistException;
import com.codessquad.qna.exception.NotAuthorizedException;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.exception.SaveFailedException;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User addUser(User newUser) {
        Optional<User> existUser = userRepository.findByUserId(newUser.getUserId());
        if (existUser.isPresent()) {
            throw new AlreadyExistException(newUser.getUserId() + " 인 Id가 이미 존재합니다.");
        }
        User added = userRepository.save(newUser);
        if (added == null) {
            throw new SaveFailedException(User.class);
        }
        return added;
    }

    public User updateUser(User user) {
        User toUpdate = userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다"));
        if (!toUpdate.verify(user)) {
            throw new NotAuthorizedException("비밀번호가 일치하지 않아 사용자 정보를 수정할 수 없습니다.");
        }
        toUpdate.update(user);
        User updated = userRepository.save(toUpdate);
        if (updated == null) {
            throw new SaveFailedException(User.class);
        }
        return updated;
    }

    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id + " 사용자를 찾을 수 없습니다"));
    }

    public User getUser(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다"));
    }
}
