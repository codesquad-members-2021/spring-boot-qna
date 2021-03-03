package com.codessquad.qna.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private final List<User> userList = new ArrayList<>();

    @PostMapping("/create")
    public String create(User user) {
        System.out.println("user Id: " + user);
        userList.add(user);
        return "redirect:/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", userList);
        return "user/list";
    }


}
