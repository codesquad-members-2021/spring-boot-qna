package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @PostMapping("/user/create")
    public String create(User user){
        System.out.println("user : " + user);
        return "welcome";
    }
}
