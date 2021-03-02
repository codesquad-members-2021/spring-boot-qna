package com.codessquad.qna.controller;

import com.codessquad.qna.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final List<User> users = new ArrayList<>();

    @GetMapping()
    public String userList(Model model) {
        model.addAttribute("users", users);
        return "users/list";
    }

    @PostMapping()
    public String registerUser(User user) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String userProfile(@PathVariable("userId") String userId, Model model) {
        for (User user : users) {
            if (userId.equals(user.getUserId())) {
                model.addAttribute("name", user.getName());
                model.addAttribute("email", user.getEmail());
                return "users/profile";
            }
        }
        return "redirect:/users";
    }



}
