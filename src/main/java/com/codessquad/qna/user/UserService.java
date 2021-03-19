package com.codessquad.qna.user;

import com.codessquad.qna.exception.LoginFailedException;
import com.codessquad.qna.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> readUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), true)
                .map(UserDTO::of)
                .collect(Collectors.toList());
    }

    public UserDTO readUser(Long id) {
        User result = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 사용자 입니다. id : " + id));

        return UserDTO.of(result);
    }

    private UserDTO readUser(String userId) {
        User result = userRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 사용자 입니다. id : " + userId));

        return UserDTO.of(result);
    }

    public UserDTO readVerifiedUser(Long id, UserDTO verificationTarget) {
        User result = readUser(id).toEntity();
        result.verifyWith(verificationTarget.toEntity());

        return UserDTO.of(result);
    }

    public UserDTO readPasswordVerifiedUser(String userId, String password) {
        try {
            User user = readUser(userId).toEntity();
            user.checkPassword(password);

            return UserDTO.of(user);

        } catch (ResourceNotFoundException | IllegalArgumentException e) {
            throw new LoginFailedException(e);
        }
    }

    public void createUser(UserDTO userDTO) {
        userRepository.findByUserId(userDTO.getUserId())
                .ifPresent(user -> {
                    throw new LoginFailedException("이미 존재하는 ID 입니다.");
                });

        userRepository.save(userDTO.toEntity());
    }

    public UserDTO updateUser(UserDTO existedUser, UserDTO newUser) {
        User userToUpdate = existedUser.toEntity();

        userToUpdate.update(newUser);
        User updatedUser = userRepository.save(userToUpdate);
        return UserDTO.of(updatedUser);
    }
}
