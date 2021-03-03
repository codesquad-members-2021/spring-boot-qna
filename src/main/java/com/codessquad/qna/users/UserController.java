package com.codessquad.qna.users;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private final List<User> users = new ArrayList<>();
    @GetMapping()
    String getUsers(Model model){
        model.addAttribute("users",users);
        return "users/list";
    }

    @PostMapping()
    String createUser(User user){
        users.add(user);
        return "redirect:/users";
    }

}
