package com.codessquad.qna.user.application;

import com.codessquad.qna.user.domain.User;
import com.codessquad.qna.user.domain.UserRepository;
import com.codessquad.qna.user.dto.UserRequest;
import com.codessquad.qna.user.dto.UserResponse;
import com.codessquad.qna.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse saveUser(UserRequest userRequest) {
        User user = userRepository.save(userRequest.toUser());
        return UserResponse.of(user);
    }

    public List<UserResponse> getUsers() {
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            userResponses.add(UserResponse.of(user));
        }
        return userResponses;
    }

    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 유저입니다; id: " + id));
        return UserResponse.of(user);
    }

    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 유저는 수정이 불가능합니다; id: " + id));
        user.update(userRequest.toUser());
        return UserResponse.of(user);
    }
}
