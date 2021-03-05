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

    @PostMapping("/users")
    public String signup(User user){
        users.add(user);
        System.out.println("user : " + user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model){
        model.addAttribute("users",users);
        return "user/list";
    }
}
