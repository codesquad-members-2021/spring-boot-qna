package com.codessquad.qna.user.application;

import com.codessquad.qna.user.domain.User;
import com.codessquad.qna.user.domain.UserRepository;
import com.codessquad.qna.user.dto.UserRequest;
import com.codessquad.qna.user.dto.UserResponse;
import com.codessquad.qna.user.exception.LoginFailedException;
import com.codessquad.qna.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse save(UserRequest userRequest) {
        User user = userRepository.save(userRequest.toUser());
        return UserResponse.from(user);
    }

    public List<UserResponse> getUsers() {
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            userResponses.add(UserResponse.from(user));
        }
        return userResponses;
    }

    public UserResponse getUser(Long id) {
        User user = getUserFromRepository(id);
        return UserResponse.from(user);
    }

    // FIXME: 나중에 LoginRequest 와 LoginResponse dto 를 사용해서 테스트 가능하게 리팩토링해야한다.
    public User login(String userId, String password) {
        User user = Optional.ofNullable(userRepository.findByUserId(userId))
                .orElseThrow(() -> new LoginFailedException("잘못된 userId 로 로그인 했습니다."));
        if (!user.matchPassword(password)) {
            throw new LoginFailedException("잘못된 password 로 로그인 했습니다.");
        }
        return user;
    }

    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = getUserFromRepository(id);
        user.update(userRequest.toUser());
        userRepository.save(user);
        return UserResponse.from(user);
    }

    private User getUserFromRepository(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
