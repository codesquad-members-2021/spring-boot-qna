package com.codessquad.qna.service;

import com.codessquad.qna.controller.UserController;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User join(User newUser) {
        // TODO: 중복가입여부 확인 로직 추
        return userRepository.save(newUser);
    }

    public Iterable<User> showAll() {
        return userRepository.findAll();
    }

    public Optional<User> showOneById(Long id) {
        return userRepository.findById(id);
    }

    public void updateInfo(User presentUser, User referenceUser) {

        referenceUser.setUserId(presentUser.getUserId());

        // TODO: 반환값을 활용하여 예외 처리, 해당 메서드에 아래 로직은 어울리지 않는다.
        userRepository.delete(presentUser);
        userRepository.save(referenceUser);
    }
}

