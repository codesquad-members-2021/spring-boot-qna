package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.dto.UserDto;
import com.codessquad.qna.exception.IllegalUserUpdateException;
import com.codessquad.qna.exception.NoSearchObjectException;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.valid.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Long join(User user) {
        UserValidator.validate(user);
        return userRepository.save(user).getId();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NoSearchObjectException("유저"));
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(o -> o.returnDto())
                .collect(Collectors.toList());
    }

    @Transactional
    public void update(User updatedUser, String newPassword, Long id) {
        UserValidator.validate(updatedUser);
        User user = findById(id);
        if (!user.checkPassword(updatedUser.getPassword())) {
            throw new IllegalUserUpdateException(id);
        }
        user.updateUserInfo(updatedUser, newPassword);
    }

    public void checkLoginable(String userId, String password) {
        User findUser = findByUserId(userId);
        if (!findUser.checkPassword(password)) {
            throw new IllegalArgumentException("비밀번호가 다름");
        }
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new NoSearchObjectException("유저"));
    }

}
