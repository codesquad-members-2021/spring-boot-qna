package com.codessquad.qna.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();

    @PostMapping("/user/create")
    public String create(User user) {
        System.out.println("user Id: " + user);
        return "welcome";
    }
}
