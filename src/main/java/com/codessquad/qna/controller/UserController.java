package com.codessquad.qna.controller;

import com.codessquad.qna.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private List<User> users = new ArrayList<User>();

    @GetMapping("/user/form")
    public String user_form() {
        return "user/form";
    }

    @PostMapping("/user/create")
    public String user_create(User user) {
        System.out.println(user);
        users.add(user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String user_list(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }
}
