package com.codessquad.qna.controller;

import com.codessquad.qna.User;
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

    @GetMapping("/user/create")
    public String toCreateUser() {
        return "form";
    }

    @PostMapping("/user/create")
    public String createUser(User user) {
        System.out.println(user);
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUser(Model model) {
        model.addAttribute("users", users);
        return "list";
    }

    @GetMapping("/users/{userId}")
    public String showProfile(@PathVariable("userId") String userId, Model model) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                model.addAttribute("user", user);
                break;
            }
        }
        return "profile";
    }
}
