package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();

    @GetMapping("/user/create")
    public String signUp(){
        return "/user/form";
    }

    @PostMapping("/user/create")
    public String create(User user) {
        System.out.println("user : " + user);
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/users/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        User selectedUser = null;
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                selectedUser = user;
            }
        }
        model.addAttribute("user", selectedUser);
        return "user/profile";
    }

}
