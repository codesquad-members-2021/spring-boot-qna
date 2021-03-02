package com.codessquad.qna.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/users")
public class UserController {
    @GetMapping
    public String getUsers() {
        return "/user/list.html";
    }

    @GetMapping("/form")
    public String getForm() {
        return "/user/form.html";
    }
}
