package com.codessquad.qna.controller;

import com.codessquad.qna.repository.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private List<User> users = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/create")
    public String toCreateUser() {
        return "signup";
    }

    @PostMapping("/user/create")
    public String createUser(User user) {
        users.add(user);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String showUser(Model model) {
        model.addAttribute("users", users);
        return "list";
    }

    @GetMapping("/users/{userId}")
    public String showProfile(@PathVariable("userId") String userId, Model model) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                model.addAttribute("user", user);
                break;
            }
        }
        return "profile";
    }

    @GetMapping("/users/{userId}/updateForm")
    public String updateForm(@PathVariable("userId") String userId, Model model) {
        for (User user : users) {
            if (user.isSameId(userId)) {
                model.addAttribute("user", user);
                break;
            }
        }
        return "updateForm";
    }

    @PostMapping("/user/{user.userId}/update")
    public String updateUser(@PathVariable("user.userId") String userId, User user) {
        for (User temp : users) {
            if (temp.isSameId(userId)) {
                users.remove(temp);
                break;
            }
        }
        users.add(user);
        return "redirect:/users";
    }
}

