package com.codessquad.qna.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/create")
    public String renderUserCreateForm() {
        return "/user/form";
    }

    @PostMapping("/create")
    public String createUser() {
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String renderUserList() {
        return "";
    }
}
