package com.codessquad.qna.web.service;

import com.codessquad.qna.web.domain.user.User;
import com.codessquad.qna.web.domain.user.UserRepository;
import com.codessquad.qna.web.dto.user.CreateUserRequest;
import com.codessquad.qna.web.exception.CrudNotAllowedException;
import com.codessquad.qna.web.exception.EntityNotFoundException;
import com.codessquad.qna.web.exception.FailedLoginException;
import com.codessquad.qna.web.utils.SessionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String userId, String password, HttpSession session) {
        if (SessionUtils.isLoginUser(session)) {
            return SessionUtils.getLoginUser(session);
        }

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("No user with userId " + userId));

        if (!user.isMatchingPassword(password)) {
            throw new FailedLoginException();
        }
        return user;
    }

    @Transactional
    public void create(CreateUserRequest request) {
        User user = request.toEntity();
        if (isUserNotExist(user)) {
            userRepository.save(user);
        } else {
            throw new CrudNotAllowedException("User Id already exists!");
        }
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public User verifiedUser(long id, User loginUser) {
        User user = findUserById(id);

        if (!loginUser.isMatchingWriter(user)) {
            throw new CrudNotAllowedException("You don't have auth");
        }
        return user;
    }

    @Transactional
    public User updateProfile(long id, String oldPassword, User updatedUser, User loginUser) {
        User user = verifiedUser(id, loginUser);
        if (user.isMatchingPassword(oldPassword)) {
            user.update(updatedUser);
            userRepository.save(user);
        }
        return user;
    }

    private boolean isUserNotExist(User user) {
        return !userRepository.existsByUserId(user.getUserId());
    }

    public User findUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No user with id number " + id));
    }
}


