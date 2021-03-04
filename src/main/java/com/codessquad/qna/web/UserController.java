package com.codessquad.qna.web;

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
        System.out.println("user Info: " + user);
        users.add(user);
        return "redirect:/";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        System.out.println(model);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String viewProfile(@PathVariable("userId") String userId, Model model) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                model.addAttribute("user", user);
                return "user/profile";
            }
        }
        return "user/profile";
    }

    @GetMapping("/users/confirm")
    public String editUserInfo() {
        return "user/confirmUserInfo";
    }

    @PostMapping("/users/confirm")
    public String editUserInfo(String userId, String password, Model model) {
        model.addAttribute("userId", userId);
        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getPassword().equals(password)) {
                return "user/updateForm";
            }
        }
        return "redirect:/users/confirm";
    }

    @PostMapping("/users/{userId}/update")
    public String changeUserInfoForm(@PathVariable("userId") String userId, String password, String name, String email) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                user.setPassword(password);
                user.setName(name);
                user.setEmail(email);
            }
        }
        return "redirect:/users";
    }

}
