package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(UserController.class);

    private List<User> users = new ArrayList<>();

    @PostMapping("")
    public String createUserAccount(User user) {
        if (user == null) {
            return "redirect:/user/form";
        }
        users.add(user);
        user.setId(users.size());
        return "redirect:/users";
    }

    @GetMapping("")
    public String showUserList(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/{userId}")
    public String showProfile(@PathVariable String userId, Model model) {
        int index = 0;
        for (User user : users) {
            if(user.getUserId().equals(userId)) {
                index = users.indexOf(user);
                logger.info("tempUser = " + users.get(index));
            }
        }
        model.addAttribute("user", users.get(index));
        return "/user/profile";
    }

    @PostMapping("/{userId}/update")
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
