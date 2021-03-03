package com.codessquad.qna.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @PostMapping("/user/create")
    public String create(UserInfo userinfo){
        System.out.println("user : " + userinfo);
        return "index";
    }
}
