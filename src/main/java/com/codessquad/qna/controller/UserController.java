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
    private List<User> users = new ArrayList<>();

    @GetMapping("/user/form")
    public String getForm(){
        return "user/form";
    }

    @PostMapping("/user/create")
    public String create(User user){
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String getUserList(Model model){
        model.addAttribute("users", users);
        return "user/list";
    }

}
