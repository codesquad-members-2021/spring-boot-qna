package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {

    @GetMapping("/user/form")
    public String moveToForm() {
        return "user/form";
    }

    @PostMapping("/user/create")
    public String createUser(User user){
        System.out.println(user);
        return "user/form";
    }
}
