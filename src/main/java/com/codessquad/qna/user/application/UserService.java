package com.codessquad.qna.user.application;

import com.codessquad.qna.user.domain.User;
import com.codessquad.qna.user.domain.UserRepository;
import com.codessquad.qna.user.dto.UserRequest;
import com.codessquad.qna.user.dto.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return userRepository.findAll()
                .stream()
                .map(UserResponse::of)
                .collect(Collectors.toList());
    }
    
    public UserResponse getUser(Long id) {
        User user = userRepository.getOne(id);
        return UserResponse.of(user);
    }

    public UserResponse updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.getOne(id);
        user.update(userRequest.toUser());
        return UserResponse.of(user);
    }
}
