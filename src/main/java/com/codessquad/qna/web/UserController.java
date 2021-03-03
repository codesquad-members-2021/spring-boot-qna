package com.codessquad.qna.web;

import com.codessquad.qna.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/users")
public class UserController {
    private List<User> userList = new ArrayList<>();

    @GetMapping("/form")
    public String getUserFormPage() {
        return "user/form";
    }

    @PostMapping("")
    public String createUser(User user) {
        System.out.println("user: " + user);
        userList.add(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String getUserList(Model model) {
        model.addAttribute("userList", userList);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String getUserProfile(@PathVariable String userId, Model model) {
        for (User user : userList) {
            if(user.matchId(userId)) {
                model.addAttribute("user", user);
            }
            System.out.println(user);
        }

        return "user/profile";
    }
}
