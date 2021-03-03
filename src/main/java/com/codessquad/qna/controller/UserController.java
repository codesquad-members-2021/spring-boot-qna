package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.UserService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
        userService.join(user);
        return "redirect:list";
    }

    @GetMapping("/user/list")
    public String userList(Model model) {
        List<User> users = userService.findUserAll();
        System.out.println("1");
        model.addAttribute("users", users);
        return "user/list";
    }
}
