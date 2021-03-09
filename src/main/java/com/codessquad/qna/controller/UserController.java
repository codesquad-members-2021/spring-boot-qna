package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final List<User> users = new ArrayList<>();

    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        User user = findUserById(userId);
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute(user);
        return "/user/profile";
    }

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }

    @PostMapping()
    public String account(User user) {
        logger.info("user: {}", user);

        user.setId(users.size() + 1);
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("{userId}/info")
    public String form(@PathVariable String userId, Model model) {
        User user = findUserById(userId);
        if (user == null) {
            return "redirect:/users";
        }
        model.addAttribute(user);
        return "/user/updateForm";
    }

    @PostMapping("{userId}/info")
    public String update(@PathVariable String userId, User userToUpdate) {
        User user = findUserById(userId);
        if (user == null) {
            return "redirect:/users";
        }
        user.update(userToUpdate);
        return "redirect:/users";
    }

    private User findUserById(String userId) {
        for (User user : users) {
            if (user.matchUserId(userId)) {
                return user;
            }
        }
        return null;
    }
}
