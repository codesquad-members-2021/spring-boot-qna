package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.HttpSessionUtils;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exception.IllegalAccessException;
import com.codessquad.qna.web.exception.NotLoginException;
import com.codessquad.qna.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/loginForm")
    public String getLoginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userService.login(userId, password);
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
        return "redirect:/";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

    @PostMapping
    public String createUser(User user) {
        userService.signUp(user);
        return "redirect:/users";
    }

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", userService.findUsers());
        return "/user/list";
    }

    @GetMapping("/{id}")
    public String getProfile(@PathVariable long id, Model model) {
        model.addAttribute("user", userService.findUser(id));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String getUpdateForm(@PathVariable long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new NotLoginException();
        }

        User loginUser = HttpSessionUtils.getSessionedUser(session);
        if (!loginUser.isSameId(id)) {
            throw new IllegalAccessException();
        }

        model.addAttribute("user", loginUser);
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable long id, String testPassword, User user, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new NotLoginException();
        }

        User loginUser = HttpSessionUtils.getSessionedUser(session);
        if (!loginUser.isSameId(id)) {
            throw new IllegalAccessException();
        }

        userService.updateUser(testPassword, loginUser, user);
        return "redirect:/users";
    }
}
