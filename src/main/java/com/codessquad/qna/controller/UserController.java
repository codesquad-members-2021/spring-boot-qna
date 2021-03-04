package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new ArrayList<User>();

    @GetMapping("/users/form")
    public String moveToForm() {
        return "users/form";
    }

    @PostMapping("/user/create")
    public String create(User user){
        System.out.println(user);
        users.add(user);
        System.out.println(users.toString());
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model){
        model.addAttribute("users",users);
        return "users/list";
    }
}
