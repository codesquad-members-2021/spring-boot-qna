package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
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

    @GetMapping("user/form")
    public String getForm() {
        return "user/form";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("user/create")
    public String create(User user, Model model) {
        users.add(user);
        System.out.println(user);
        model.addAttribute("user", user);
        return "user/list";
    }

    @GetMapping("user/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                model.addAttribute("user", user);
            }
        }
        return "user/profile";
    }
}
