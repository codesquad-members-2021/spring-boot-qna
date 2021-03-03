package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private List<User> userList = new ArrayList<>();

    @PostMapping("/user/create")
    public String create(User user) {
        System.out.println("user: " + user.toString());
        userList.add(user);
        return "redirect:/user/list.html";
    }
}
