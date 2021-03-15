package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private List<User> users = new ArrayList<>();

    @PostMapping("")
    public String createUserAccount(User user) {
        System.out.println("user = " + user);
        users.add(user);
        if (user == null) {
            return "redirect:/user/form";

        }else {
            return "redirect:/users";
        }
    }

    @GetMapping("")
    public String showUserList(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/{userId}")
    public String showProfile(@PathVariable String userId, Model model) {
        User tempUser = null;
        for (User user : users) {
            if(user.getUserId().equals(userId)) {
                int index = users.indexOf(user);
               tempUser = users.get(index);
                System.out.println("tempUser = " + tempUser);
            }
        }
        model.addAttribute("user", tempUser);
        return "/user/profile";
    }

    @PostMapping("/update/{userId}")
    public String updateUserInfo(@PathVariable String userId, User updateUser) {
        for (User user : users) {
            if(user.getUserId().equals(userId)) {
                int index = users.indexOf(user);
                users.set(index, updateUser);
                System.out.println("updateUser = " + updateUser);
            }
        }
        return "redirect:/users";

    }

}
