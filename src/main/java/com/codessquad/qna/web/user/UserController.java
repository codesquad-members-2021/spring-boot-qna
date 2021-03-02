package com.codessquad.qna.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private List<User> userList = new ArrayList<>();

    @PostMapping("users")
    public String onRegister(String userId, String password, String name, String email) {
        User createdUser = new User(userId, password, name, email);
        userList.add(createdUser);
        return "redirect:/users";
    }

    @GetMapping("users")
    public String onList(Model model) {
        model.addAttribute("users", userList);
        return "user/list";
    }

    @GetMapping("users/{userId}")
    public String onUserProfile(@PathVariable("userId") String userId, Model model) {
        User foundUser = userList.stream()
                .filter((user -> user.getUserId().equals(userId)))
                .findFirst().orElse(null);
        model.addAttribute("user", foundUser);
        return "user/profile";
    }
}
