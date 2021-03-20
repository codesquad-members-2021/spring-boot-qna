package com.codessquad.qna.user;

import com.codessquad.qna.exception.LoginFailedException;
import com.codessquad.qna.exception.ResourceNotFoundException;
import com.codessquad.qna.exception.UserExistedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> readAll() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), true)
                .map(UserDTO::from)
                .collect(Collectors.toList());
    }

    private User readExistedUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 사용자 입니다. id : " + id));
    }

    private User readExistedUser(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 사용자 입니다. id : " + userId));
    }

    public UserDTO read(Long id) {
        User result = readExistedUser(id);

        return UserDTO.from(result);
    }

    public UserDTO readVerifiedUser(Long id, UserDTO verificationTarget) {
        User result = readExistedUser(id);
        result.verifyWith(verificationTarget.toEntity());

        return UserDTO.from(result);
    }

    public UserDTO readPasswordVerifiedUser(String userId, String password) {
        try {
            User user = readExistedUser(userId);
            user.checkPassword(password);

            return UserDTO.from(user);

        } catch (ResourceNotFoundException | IllegalArgumentException e) {
            throw new LoginFailedException(e);
        }
    }

    public void create(UserDTO user) {
        Optional<User> existedUser = userRepository.findByUserId(user.getUserId());

        if (existedUser.isPresent()) {
            throw new UserExistedException();
        }

        userRepository.save(user.toEntity());
    }

    public UserDTO update(UserDTO existedUser, UserDTO newUser) {
        User userToUpdate = existedUser.toEntity();

        userToUpdate.update(newUser);
        User updatedUser = userRepository.save(userToUpdate);
        return UserDTO.from(updatedUser);
    }
}
