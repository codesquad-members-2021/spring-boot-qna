package com.codessquad.qna.service;

import com.codessquad.qna.dto.UserDto;
import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.UserNotFoundException;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.util.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void save(UserDto userDto) {
        userRepository.save(Mapper.mapToUser(userDto));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public void change(Long id, UserDto updateUserDto) {
        User oldUser = getUserById(id);
        oldUser.change(Mapper.mapToUser(updateUserDto));
        userRepository.save(oldUser);
    }

    public boolean isMatchedUserAndPassword(User user, String password) {
        if(!user.isMatchedPassword(password)) {
            logger.info("User password not matched : {}", user);
            return false;
        }
        return true;
    }

    public void removeUser(Long id) {
        userRepository.deleteUserById(id);
    }

    public int countOfUsers() {
        return (int) userRepository.count();
    }

}
