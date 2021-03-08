package com.codessquad.qna.controller;

import com.codessquad.qna.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();

    @PostMapping("/users")
    public String create(User user) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "users/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable("userId") String userId, Model model) {
        for (User user : users) {
            if (user.getUserId().equals(userId))
                model.addAttribute("user", user);
        }
        return "users/profile";
    }

    @GetMapping("/users/{userId}/form")
    public String updateForm(@PathVariable("userId") String userId, Model model) {
        for (User user : users) {
            if (user.getUserId().equals(userId))
                model.addAttribute("user", user);
        }
        return "users/updateForm";
    }

    @PostMapping("/users/{userId}/update")
    public String update(@PathVariable("userId") String userId, User updateUserInfo, Model model) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                user.setName(updateUserInfo.getName());
                user.setEmail(updateUserInfo.getEmail());
            }
            model.addAttribute("user", user);
        }
        return "redirect:/users";
    }
}
