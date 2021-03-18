package com.codessquad.qna.user;

import com.codessquad.qna.exception.LoginFailedException;
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
                .map(User::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO readUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 입니다. id : " + id))
                .toDTO();
    }

    public UserDTO readVerifiedUser(Long id, UserDTO verificationTarget) {
        User result = readUser(id).toEntity();

        result.verifyWith(verificationTarget.toEntity());

        return result.toDTO();
    }

    public UserDTO readLoginableUser(String userId, String password) {
        try {
            User user = userRepository.findByUserId(userId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 입니다. id : " + userId));

            user.checkPassword(password);

            return user.toDTO();

        } catch (IllegalArgumentException e) {
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

        return userRepository.save(userToUpdate).toDTO();
    }
}
