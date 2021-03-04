package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private List<User> userList = new ArrayList<>();

    @PostMapping("/user/create")
    public String create(User user) {
        System.out.println("user: " + user.toString());
        userList.add(user);
        return "redirect:list";
    }

    @GetMapping("/user/list")
    public String list(Model model) {
        model.addAttribute("userList", userList);
        return "list";
    }

    @GetMapping("/userList/{userId}")
    @ResponseBody
    public String profile(@PathVariable String userId, Model model) {
        for (User user : userList) {
            if(user.getUserId() == userId) {
                model.addAttribute("user", user);
            }
        }
        return "profile";
    }
}
