package com.codessquad.qna.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();

    @PostMapping("/user/create")
    public String create(User user){
        users.add(user);
        System.out.println("user : " + user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String list(Model model){
        model.addAttribute("users",users);
        return "list";
    }
}
