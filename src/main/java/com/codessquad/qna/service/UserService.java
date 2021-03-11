package com.codessquad.qna.service;

import com.codessquad.qna.controller.UserController;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User newUser) {

        // TODO: 기존 회원과의 중복 여부 확인 로직 추가

        userRepository.save(newUser);

        logger.info("saved user: " + newUser.toString());
        // TODO: 반환값으로 Long id(.getId) 설정해서 컨트롤러에서 정상 join 여부 확인할 수 있도록
    }

    public Iterable<User> showAll() {
        return userRepository.findAll();
    }

    // TODO: Optional 객체에서 원 객체 추출하는 방법 학습
    public User showOneById(Long id) {
        return userRepository.findById(id).get();
    }

    public void updateInfo(User presentUser, User referenceUser) {

        referenceUser.setUserId(presentUser.getUserId());

        // TODO: 반환값을 활용하여 예외 처리, 해당 메서드에 아래 로직은 어울리지 않는다.
        userRepository.delete(presentUser);
        userRepository.save(referenceUser);
    }
}

