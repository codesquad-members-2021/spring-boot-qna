package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public String createUser(User user) {
        try {
            userService.join(user);
        } catch (IllegalStateException e) {
            return "redirect:/users/join_failed";
        }
        return "redirect:/users/list";
    }

    @GetMapping("/users/list")
    public String userList(Model model) {
        List<User> users = userService.findUserAll();
        model.addAttribute("users", users);
        return "user/list";
    }


    @GetMapping("/users/{userId}")
    public String userProfile(@PathVariable String userId, Model model) {
        User user = userService.findUserByUserId(userId);
        model.addAttribute("user", user);
        return "user/profile";
    }
}
