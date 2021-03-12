package com.codessquad.qna.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "user/list";
    }

    @PostMapping
    public String createUser(User user) {
        userService.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}/profile")
    public String userProfile(@PathVariable String userId, Model model) {
        Optional<User> user = userService.getUser(userId);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "user/profile";
        }
        return "redirect:/users";
    }

    @GetMapping("/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model) {
        Optional<User> user = userService.getUser(userId);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "user/updateForm";
        }
        return "redirect:/users";
    }

    @PostMapping("/{userId}/update")
    public String updateUser(User user) {
        userService.updateUser(user);
        return "redirect:/users";
    }
}
