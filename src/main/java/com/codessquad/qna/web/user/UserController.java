package com.codessquad.qna.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private List<User> userList = new ArrayList<>();

    @PostMapping("user/create")
    public String onRegister(String userId, String password, String name, String email) {
        User createdUser = new User(userId, password, name, email);
        userList.add(createdUser);
        return "redirect:/user/list";
    }

    @GetMapping("user/list")
    public String onList(Model model) {
        model.addAttribute("users", userList);
        return "user/list";
    }
}
