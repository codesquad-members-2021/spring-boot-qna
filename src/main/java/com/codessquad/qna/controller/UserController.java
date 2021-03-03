package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.UserService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService = new UserService();

    @GetMapping("/user/form")
    public String userForm() {
        return "user/form";
    }

    @PostMapping("user/form")
    public String createUser(User user) {
        try {
            userService.join(user);
        } catch (IllegalStateException e) {
            User.serialCode -= 1;
            return "redirect:join_failed";
        }
        return "redirect:list";
    }

    @GetMapping("/user/list")
    public String userList(Model model) {
        List<User> users = userService.findUserAll();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/user/join_failed")
    public String failToJoin() {
        return "user/join_failed";
    }

    @GetMapping("/user/{userId}")
    public String userProfile(@PathVariable String userId, Model model) {
        User user = userService.findUserByUserId(userId);
        model.addAttribute("user", user);
        return "user/profile";
    }
}
