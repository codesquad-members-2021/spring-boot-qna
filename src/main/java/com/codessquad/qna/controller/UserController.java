package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.UserService;
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

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.users());
        return "users/list";
    }

    @PostMapping
    public String register(User user) {
        userService.register(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.user(id));
        return "users/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable("id") Long id, HttpSession session, Model model) {
        userService.checkAccessId(HttpSessionUtils.loginUser(session), id);
        model.addAttribute(userService.user(id));
        return "users/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, String oldPassword, User newUserInfo, HttpSession session) {
        userService.checkAccessId(HttpSessionUtils.loginUser(session), id);
        userService.update(id, oldPassword, newUserInfo);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "users/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        HttpSessionUtils.setUserSession(session, userService.authenticate(userId, password));
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        HttpSessionUtils.removeUserSession(session);
        return "redirect:/";
    }

}
