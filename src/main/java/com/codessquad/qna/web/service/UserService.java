package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.domain.UserRepository;
import com.codessquad.qna.web.exceptions.auth.LoginFailedException;
import com.codessquad.qna.web.exceptions.auth.UnauthorizedAccessException;
import com.codessquad.qna.web.exceptions.users.RequestToCreateDuplicatedUserException;
import com.codessquad.qna.web.exceptions.users.UserNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import static com.codessquad.qna.web.exceptions.users.RequestToCreateDuplicatedUserException.DUPLICATED_ID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        try {
            user.verifyUserEntityIsValid();
            userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new RequestToCreateDuplicatedUserException(DUPLICATED_ID);
        }
    }

    public Iterable<User> users() {
        return userRepository.findAll();
    }

    public User userDetail(long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public void modifyUser(long id, String prevPassword, User newUserInfo, User loginUser) {
        newUserInfo.verifyUserEntityIsValid();
        User foundUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        loginUser.verifyIsSameUser(foundUser);
        loginUser.verifyPassword(prevPassword);

        loginUser.update(newUserInfo);
        userRepository.save(loginUser);
    }

    public User doLogin(String userId, String password) {
        try {
            User foundUser = userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
            foundUser.verifyPassword(password);
            return foundUser;
        } catch (UserNotFoundException exception) {
            throw new LoginFailedException("존재하지 않는 계정입니다");
        } catch (UnauthorizedAccessException exception) {
            throw new LoginFailedException("패스워드가 일치하지 않습니다");
        }
    }


}
