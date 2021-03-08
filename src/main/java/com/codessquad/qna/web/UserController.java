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
    private final List<User> userList = new ArrayList<>();

    @PostMapping("/create")
    public String create(User user) {
        userList.add(user);
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String getList(Model model) {
        model.addAttribute("users", userList);
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String getOneUserProfile(@PathVariable("userId") String userId, Model model) {
        User foundUser = getUserById(userId);
        model.addAttribute("user", foundUser);
        return "user/profile";
    }

    public User getUserById(String userId){
        return userList.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst().orElse(null);
    }
}
