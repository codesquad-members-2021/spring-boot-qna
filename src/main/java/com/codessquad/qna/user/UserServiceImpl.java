package com.codessquad.qna.user;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> getUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), true)
                .map(User::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 입니다. id : " + id))
                .toDTO();
    }

    @Override
    public UserDTO getUserWithVerifyPassword(String userId, String password) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> HttpClientErrorException.create(
                        User.LOGIN_FAIL_MESSAGE,
                        HttpStatus.UNAUTHORIZED,
                        "",
                        null,
                        null,
                        StandardCharsets.UTF_8
                ))
                .toDTO();
    }

    @Override
    public void createUser(UserDTO userDTO) {
        userRepository.save(userDTO.toEntity());
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO newUser) {
        User existedUser = getUser(id).toEntity();

        existedUser.checkPassword(newUser.getPassword());
        existedUser.update(newUser);

        return userRepository.save(existedUser).toDTO();
    }
}
