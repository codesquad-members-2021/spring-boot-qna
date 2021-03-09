package com.codessquad.qna.web.controllers;

import com.codessquad.qna.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();

    @GetMapping("/user/login")
    public String getLogin() {
        return "user/login";
    }

    @GetMapping("/user/form")
    public String getForm() {
        return "user/form";
    }

    @PostMapping("/user/form")
    public String createUser(User user) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String getUserList(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String getSpecificUser(@PathVariable("userId") String userId, Model model) {
        User foundUser = findUserById(userId);
        model.addAttribute("user", foundUser);
        return "user/profile";
    }

    private User findUserById(String userId) {
        return users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findAny().orElse(null);
    }

}
