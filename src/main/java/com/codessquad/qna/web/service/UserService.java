package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.domain.UserRepository;
import com.codessquad.qna.web.exceptions.InvalidEntityException;
import com.codessquad.qna.web.exceptions.auth.LoginFailedException;
import com.codessquad.qna.web.exceptions.auth.UnauthorizedAccessException;
import com.codessquad.qna.web.exceptions.users.UserNotFoundException;
import org.springframework.stereotype.Service;

import static com.codessquad.qna.web.utils.ExceptionConstants.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        verifyUserEntityIsValid(user);
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
        verifyIsSameUser(loginUser, foundUser);
        verifyPassword(loginUser, prevPassword);
        verifyUserEntityIsValid(newUserInfo);

        loginUser.update(newUserInfo);
        userRepository.save(loginUser);
    }

    public User doLogin(String userId, String password) {
        try {
            User foundUser = userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
            verifyPassword(foundUser, password);
            return foundUser;
        } catch (UserNotFoundException exception) {
            throw new LoginFailedException("존재하지 않는 계정입니다");
        } catch (UnauthorizedAccessException exception) {
            throw new LoginFailedException("패스워드가 일치하지 않습니다");
        }
    }

    private void verifyIsSameUser(User user, User anotherUser) {
        if (!user.isMatchingId(anotherUser)) {
            throw new UnauthorizedAccessException(CANNOT_MODIFY_ANOTHER_USER);
        }
    }

    private void verifyPassword(User user, String password) {
        if (!user.isMatchingPassword(password)) {
            throw new UnauthorizedAccessException(PASSWORD_NOT_MATCHING);
        }
    }

    private void verifyUserEntityIsValid(User user) {
        if (!user.isValid()) {
            throw new InvalidEntityException(EMPTY_FIELD_IN_USER_ENTITY);
        }
    }
}
