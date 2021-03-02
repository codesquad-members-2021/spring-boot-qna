package com.codessquad.qna.controller;

import com.codessquad.qna.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private List<User> userList = new ArrayList<>();

    @GetMapping()
    public String userList() {
        return "user/list";
    }

    @PostMapping()
    public String registerUser(User user) {
        userList.add(user);
        return "redirect:/users";
    }

}
