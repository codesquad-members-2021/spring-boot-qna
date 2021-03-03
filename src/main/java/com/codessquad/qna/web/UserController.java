package com.codessquad.qna.web;

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

    @PostMapping("/users")
    public String createUser(User user) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", users);
        return "/users/list";
    }

    @GetMapping("/users/{userId}")
    public String getProfile(@PathVariable String userId, Model model) {
        //id 중복 확인, null일경우 처리
        User foundUser = fingByUserId(userId);
        model.addAttribute("user", foundUser);
        return "/users/profile";
    }

    private User fingByUserId(String userId) {
        User foundUser = null;
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                foundUser = user;
            }
        }
        return foundUser;
    }
}
