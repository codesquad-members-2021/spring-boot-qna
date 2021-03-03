package com.codessquad.qna.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @PostMapping("/user/create")
    public String create(String userId, String password, String name, String email){
        System.out.println("userId : " + userId);
        return "index";
    }
}
