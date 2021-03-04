package com.codessquad.qna.controller;

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
    public String create(String userId, String password, String name, String email){
        System.out.printf("%s %s %s %s ", userId, password, name, email);
        return "user/list";
    }

}
