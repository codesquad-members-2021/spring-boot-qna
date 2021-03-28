package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import com.codessquad.qna.exception.LoginFailedException;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.codessquad.qna.exception.ExceptionMessages.FAILED_LOGIN;
import static com.codessquad.qna.exception.ExceptionMessages.PROFILE_MODIFICATION_FAIL;
import static com.codessquad.qna.utils.SessionUtil.*;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User showProfile(Long id) {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }


    public void updateUser(Long id, String pastPassword, User updatedUser, HttpSession session) {
        User sessionUser = getLoginUser(session);
        User currentUser = userRepository.findById(id).orElseThrow(NotFoundException::new);

        if (!currentUser.isMatchingPassword(pastPassword)) {
            logger.info(PROFILE_MODIFICATION_FAIL);
            throw new UnauthorizedException(PROFILE_MODIFICATION_FAIL);
        }

        if (sessionUser.equals(updatedUser)) {
            sessionUser.update(updatedUser);
        }

        userRepository.save(sessionUser);
        logger.info("update User {}", sessionUser.getUserId());
    }

    public void validationCheck(Long id, HttpSession session) {
        User foundUser = userRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!isValidUser(session, foundUser)) {
            logger.info(PROFILE_MODIFICATION_FAIL);
            throw new UnauthorizedException(PROFILE_MODIFICATION_FAIL);
        }
    }

    public void login(String userId, String password, HttpSession session) {
        User foundUser = userRepository.findByUserId(userId).orElseThrow(NotFoundException::new);

        if (!foundUser.isMatchingPassword(password)) {
            throw new LoginFailedException(FAILED_LOGIN);
        }

        logger.info("Login Success");
        setLoginUser(session, foundUser);
    }
}
