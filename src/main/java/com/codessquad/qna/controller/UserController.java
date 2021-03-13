package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import com.codessquad.qna.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping()
    public ModelAndView userList() {
        ModelAndView modelAndView = new ModelAndView("users/list");
        modelAndView.addObject("users", userRepository.findAll());
        return modelAndView;
    }

    @PostMapping()
    public String registerUser(User user) {
        userRepository.findByUserId(user.getUserId())
                .ifPresent(u -> {
                    throw new UserExistException();
                });
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public ModelAndView userProfile(@PathVariable("id") Long id) {
        return new ModelAndView("users/profile",
                "user", userRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @GetMapping("/{id}/form")
    public ModelAndView updateUserForm(@PathVariable("id") Long id, HttpSession session) {
        checkSessionWithId(session, id);
        return new ModelAndView("users/updateForm",
                "user", userRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") Long id, String oldPassword, User newUserInfo, HttpSession session) {
        checkSessionWithId(session, id);
        User user = userRepository.findById(id)
                .filter(u -> u.isMatchingPassword(oldPassword))
                .orElseThrow(() -> new UnauthorizedAccessException("권한이 존재하지 않습니다."));
        user.update(newUserInfo);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "users/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId)
                .filter(u -> u.isMatchingPassword(password))
                .orElseThrow(LoginFailedException::new);
        HttpSessionUtils.setUserSession(session, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        HttpSessionUtils.removeUserSession(session);
        return "redirect:/";
    }

    private void checkSessionWithId(HttpSession session, Long accessId) {
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionUser.isMatchingId(accessId)) {
            throw new UnauthorizedAccessException("다른 사람의 정보를 수정할 수 없습니다.");
        }
    }
}
