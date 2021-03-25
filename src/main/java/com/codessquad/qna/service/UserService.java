package com.codessquad.qna.service;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import com.codessquad.qna.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;

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
            logger.info("password is not Matching, please re-try ");
            //return "redirect:/user/login";
            //@Todo 예외발생시키

        }

        if (sessionUser.equals(updatedUser)) {
            sessionUser.update(updatedUser);
        }

        userRepository.save(sessionUser);
        logger.info("update User {}", sessionUser.getUserId());
    }

    public void validationCheck(Long id, Model model, HttpSession session) {
        //id =>> user's Id
        User foundUser = userRepository.findById(id).orElseThrow(NotFoundException::new);
        if (!isValidUser(session, foundUser)) {
            logger.info("Login Failure : wrong password");
            //return "redirect:/user/form";
            //@Todo  에러발생, 권한없는 사용자의 시도
        }
    }


    public void login(String userId, String password, HttpSession session) {
        User foundUser = userRepository.findByUserId(userId).orElseThrow(NotFoundException::new); //get 안티패턴 수정해야함@@@@@@@@@@@@

        if (!foundUser.isMatchingPassword(password)) {
            logger.info("Login Failure : wrong password");
            //return "redirect:/user/form";
            //@Todo 예외발생, 매칭되지 않는 비번과 아이디
        }

        logger.info("Login Success");
        setLoginUser(session, foundUser);
    }
}
