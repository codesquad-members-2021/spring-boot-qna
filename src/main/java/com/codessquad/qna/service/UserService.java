package com.codessquad.qna.service;

import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.domain.user.UserRepository;
import com.codessquad.qna.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 회원 가입
     */
    public User signUp(User user) {
        Optional<User> result = userRepository.findByUserId(user.getUserId());
        validateDuplicateUser(result);
        userRepository.save(user);
        return user;
    }

    private void validateDuplicateUser(Optional<User> result) {
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    /**
     * 회원 조회
     */
    public User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public User findUser(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    /**
     * 전체 회원 조회
     */
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    /**
     * 회원 업데이트
     */
    @Transactional
    public User update(User updateUser) {
        User user = userRepository.findByUserId(updateUser.getUserId())
                .orElseThrow(UserNotFoundException::new);

        return user.update(updateUser);
    }
}
