package com.codessquad.qna.user.application;


import com.codessquad.qna.user.domain.User;
import com.codessquad.qna.user.domain.UserRepository;
import com.codessquad.qna.user.dto.UserRequest;
import com.codessquad.qna.user.dto.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse saveUser(UserRequest userRequest) {
        // TODO: 동일한 userId 룰 추가하려 할 경우에 대한 예외처리
        User user = userRepository.save(userRequest.toUser());
        return UserResponse.of(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(Long id) {
        // TODO: 조회 실패에 대한 예외처리
        User user = userRepository.getOne(id);
        return UserResponse.of(user);
    }

    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.getOne(id);
        user.update(userRequest.toUser());
        return UserResponse.of(user);
    }
}
