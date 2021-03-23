package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.domain.UserRepository;
import com.codessquad.qna.web.exceptions.auth.LoginFailedException;
import com.codessquad.qna.web.exceptions.auth.UnauthorizedAccessException;
import com.codessquad.qna.web.exceptions.users.UserNotFoundException;
import org.springframework.stereotype.Service;

import static com.codessquad.qna.web.utils.ExceptionConstants.CANNOT_MODIFY_ANOTHER_USER;
import static com.codessquad.qna.web.utils.ExceptionConstants.PASSWORD_NOT_MATCHING;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public Iterable<User> users() {
        return userRepository.findAll();
    }

    public User userDetail(long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public void modifyUser(long id, String prevPassword, User newUserInfo, User loginUser) {
        User foundUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        if (!loginUser.isMatchingId(foundUser)) {
            throw new UnauthorizedAccessException(CANNOT_MODIFY_ANOTHER_USER);
        }
        verifyAuthorizedAccess(loginUser, prevPassword);
        loginUser.update(newUserInfo);
        userRepository.save(loginUser);
    }

    public User doLogin(String userId, String password) {
        User foundUser = null;
        try {
            foundUser = userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
            verifyAuthorizedAccess(foundUser, password);
        } catch (UnauthorizedAccessException | UserNotFoundException exception) {
            throw new LoginFailedException("존재하지 않는 계정이거나 패스워드가 일치하지 않습니다");
        }
        return foundUser;
    }

    private void verifyAuthorizedAccess(User user, String password) {
        if (!user.isMatchingPassword(password)) {
            throw new UnauthorizedAccessException(PASSWORD_NOT_MATCHING);
        }
    }
}
