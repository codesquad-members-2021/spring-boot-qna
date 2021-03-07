package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private List<User> users = new ArrayList<>();

    @PostMapping("/")
    public String create(User user) {
        users.add(user);
        System.out.println(user);
        return "redirect:/user/";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                model.addAttribute("user", user);
            }
        }
        return "user/profile";
    }

    @GetMapping("/{userId}/form")
    public String showModifyProfile(@PathVariable String userId, Model model) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                model.addAttribute(user);
            }
        }
        return "user/updateForm";
    }

    @PostMapping("/{userId}/update")
    public String modifyProfile(User updatedUser, Model model) {
        for (User user : users) {
            if (user.getUserId().equals(updatedUser.getUserId())) {
                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
            }
        }
        model.addAttribute("users", users);
        return "redirect:/user/";
    }
}
