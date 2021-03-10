package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String createUser(User user) {
        try {
            userService.signUp(user);
        } catch (IllegalStateException e) {
            return "redirect:/";
        }
        return "redirect:/users";
    }

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", userService.findUsers());
        return "/user/list";
    }

    @GetMapping("/{userId}")
    public String getProfile(@PathVariable String userId, Model model) {
        try {
            model.addAttribute("user", userService.findUser(userId));
        } catch (IllegalStateException e) {
            return "redirect:/";
        }
        return "/user/profile";
    }

    @GetMapping("/{userId}/form")
    public String getUpdateForm(@PathVariable String userId, Model model) {
        try {
            model.addAttribute("user", userService.findUser(userId));
        } catch (IllegalStateException e) {
            return "redirect:/";
        }
        return "/user/updateForm";
    }

    @PostMapping("/{userId}/update")
    public String updateUser(User user) {
        try {
            userService.updateUser(user);
        } catch (IllegalStateException e) {
            return "redirect:/";
        }
        return "redirect:/users";
    }
}
