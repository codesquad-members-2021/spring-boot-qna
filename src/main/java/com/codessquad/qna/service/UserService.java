package com.codessquad.qna.service;

import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Long create(User user) {
        return userRepository.save(user).getId();
    }

    @Transactional
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. id = " + id));
    }

    @Transactional
    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    @Transactional
    public Long update(Long id, User userWithUpdatedInfo) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. id = " + id));
        if (!user.isCorrectPassword(userWithUpdatedInfo.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        user.update(userWithUpdatedInfo);

        return id;
    }
}
