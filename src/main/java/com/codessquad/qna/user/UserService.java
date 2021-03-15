package com.codessquad.qna.user;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), true)
                .map(User::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 입니다. id : " + id))
                .toDTO();
    }

    public UserDTO getUserWithVerifyPassword(String userId, String password) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> HttpClientErrorException.create(
                        User.LOGIN_FAIL_MESSAGE,
                        HttpStatus.UNAUTHORIZED,
                        "",
                        null,
                        null,
                        StandardCharsets.UTF_8
                ));

        user.checkPassword(password);

        return user.toDTO();
    }

    public void createUser(UserDTO userDTO) {
        userRepository.save(userDTO.toEntity());
    }

    public UserDTO updateUser(Long id, UserDTO newUser) {
        User existedUser = getUser(id).toEntity();

        existedUser.checkPassword(newUser.getPassword());
        existedUser.update(newUser);

        return userRepository.save(existedUser).toDTO();
    }
}
