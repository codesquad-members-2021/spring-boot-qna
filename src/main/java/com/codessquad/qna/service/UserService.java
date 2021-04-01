package com.codessquad.qna.service;

import com.codessquad.qna.exception.EntityNotFoundException;
import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.exception.UserAccountException;
import com.codessquad.qna.model.User;
import com.codessquad.qna.model.dto.UserDto;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.exception.ErrorMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(UserDto userDto) {
        if (userRepository.findByUserId(userDto.getUserId()).isPresent()) {
            throw new UserAccountException(ErrorMessage.DUPLICATED_ID);
        }
        userRepository.save(userDto.toEntity());
    }

    public UserDto login(String userId, String password) {
        User user = userRepository.findByUserIdAndPassword(userId, password).orElseThrow(
                () -> new UserAccountException(ErrorMessage.LOGIN_FAILED));
        return new UserDto(user);
    }

    public void update(Long id, UserDto targetUserDto, String currentPassword, UserDto sessionedUserDto) {
        UserDto userDto = verifyUser(id, sessionedUserDto);
        if (!userDto.matchPassword(currentPassword)) {
            throw new UserAccountException(ErrorMessage.WRONG_PASSWORD);
        }
        userDto.update(targetUserDto);
        userRepository.save(userDto.toEntity());
    }

    public UserDto verifyUser(Long id, UserDto sessionedUserDto) {
        if (!sessionedUserDto.matchId(id)) {
            throw new IllegalUserAccessException();
        }
        return sessionedUserDto;
    }

    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));
        return new UserDto(user);
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }
}
