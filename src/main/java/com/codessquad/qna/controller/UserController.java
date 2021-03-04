package com.codessquad.qna.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("users")
public class UserController {
    private final List<User> users = new ArrayList<>();

    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                model.addAttribute(user);
            }
        }
        return "/user/profile";
    }

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }

    @PostMapping("/create")
    public String create(User user) {
        System.out.println(user);
        users.add(user);
        return "redirect:/users";
    }
}
