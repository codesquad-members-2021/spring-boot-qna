package com.codessquad.qna.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    private String userList(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "user/list";
    }

    @PostMapping
    private String createUser(User user) {
        userService.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}/profile")
    private String userProfile(@PathVariable String userId, Model model) {
        Optional<User> user = userService.getUser(userId);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "user/profile";
        }
        return "redirect:/users";
    }

    @GetMapping("/{userId}/form")
    private String updateForm(@PathVariable String userId, Model model) {
        Optional<User> user = userService.getUser(userId);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "user/updateForm";
        }
        return "redirect:/users";
    }

    @PostMapping("/{id}/update")
    private String updateUser(User user) {
        userService.updateUser(user);
        return "redirect:/users";
    }
}
