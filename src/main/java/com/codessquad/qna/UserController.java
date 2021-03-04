package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    List<User> users = new ArrayList<>();

    @PostMapping("/user/create")
    public String createUser(User user) {
        users.add(user);
        return "redirect:/users";
    }


}
