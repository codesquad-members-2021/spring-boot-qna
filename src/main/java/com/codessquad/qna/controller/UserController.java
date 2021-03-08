package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        model.addAttribute("user", getUserByUserId(userId));
        return "user/profile";
    }

    private User getUserByUserId(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    @GetMapping("/{index}/form")
    public String update(@PathVariable int index, Model model) {
        try {
            model.addAttribute("user", users.get(index - 1));
            return "/user/updateForm";
        } catch (IndexOutOfBoundsException e) {
            return "redirect:/";
        }
    }

    @PostMapping("/{index}")
    public String updateForm(@PathVariable int index, User newUser) {
        User user = users.get(index - 1);
        user.update(newUser);
        return "redirect:/users";
    }
}
