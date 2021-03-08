package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public ModelAndView userProfile(@PathVariable("id") Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return new ModelAndView("redirect:/users");
        }
        ModelAndView modelAndView = new ModelAndView("users/profile");
        modelAndView.addObject(user.get());
        return modelAndView;
    }

    @GetMapping("/{id}/form")
    public ModelAndView updateUserForm(@PathVariable("id") Long id, HttpSession session) {
        if (!HttpSessionUtils.isLogined(session)) {
            return new ModelAndView("redirect:/users/login");
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionUser.isMatchingId(id)) {
            throw new IllegalStateException("다른 유저의 정보를 수정할 수 없습니다.");
        }
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저"));
        return new ModelAndView("users/update_form", "user", user);
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") Long id, String oldPassword, User newUserInfo, HttpSession session) {
        if (!HttpSessionUtils.isLogined(session)) {
            return "redirect:/users/login";
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionUser.isMatchingId(id)) {
            throw new IllegalStateException("다른 유저의 정보를 수정할 수 없습니다.");
        }
        userRepository.findById(id)
                .filter(user -> user.isMatchingPassword(oldPassword))
                .ifPresent(user -> {
                    user.update(newUserInfo);
                    userRepository.save(user);
                });
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "users/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        Optional<User> user = userRepository.findByUserId(userId);
        if (!user.filter(u -> u.isMatchingPassword(password)).isPresent()) {
            return "redirect:/users/login";
        }
        session.setAttribute("sessionUser", user.get());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        HttpSessionUtils.removeUserSession(session);
        return "redirect:/";
    }
}
