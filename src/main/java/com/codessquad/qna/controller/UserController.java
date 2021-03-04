package com.codessquad.qna.controller;

import com.codessquad.qna.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @GetMapping("/user/form")
    public String getForm(){
        return "user/form";
    }

    @PostMapping("/user/create")
    public String create(User user){
        System.out.printf("%s ", user);
        return "user/list";
    }

}
