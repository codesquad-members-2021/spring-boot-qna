package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.*;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.codessquad.qna.controller.HttpSessionUtils.getSessionUser;
import static com.codessquad.qna.controller.HttpSessionUtils.isLoginUser;
import static com.codessquad.qna.domain.User.isValidPassword;


@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void join(User user) {
        userRepository.findByUserId(user.getUserId())
                .ifPresent(u -> {
                    throw new DuplicateUserIdFoundException();
                });
        userRepository.save(user);
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public void update(User user, User updatedUser) {
        user.update(updatedUser);
        userRepository.save(user);
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findUser(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User findByUserId(String userId) {
        return userRepository.findByUserId(userId).orElseThrow(UserNotFoundException::new);
    }

    public User findVerifiedUser(Long id, HttpSession session) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        checkPermission(id, session);
        return user;
    }

    private void checkPermission(Long id, HttpSession session) {
        if (!isLoginUser(session)) {
            throw new IllegalUserAccessException("로그인이 필요합니다.");
        }
        User loginUser = getSessionUser(session);
        if (!loginUser.equals(getSessionUser(session))) {
            throw new IllegalUserAccessException();
        }
    }

    public boolean isValidInput(User user, String password, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("errorMessage", "비어있는 필드가 있습니다.");
            return false;

        }
        if (!isValidPassword(user, password)) {
            model.addAttribute("user", user);
            model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
            return false;
        }
        return true;
    }
}

