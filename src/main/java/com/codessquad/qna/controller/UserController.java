package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        User user = userService.findUserById(userId);
        if (user == null) {
            return "redirect:/";
        }
        model.addAttribute(user);
        return "/user/profile";
    }

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "/user/list";
    }

    @PostMapping()
    public String account(User user) {
        logger.info("user: {}", user);

        userService.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("{userId}/info")
    public String form(@PathVariable String userId, Model model) {
        User user = userService.findUserById(userId);
        if (user == null) {
            return "redirect:/users";
        }
        model.addAttribute(user);
        return "/user/updateForm";
    }

    @PostMapping("{userId}/info")
    public String update(@PathVariable String userId, User userToUpdate) {
        User user = userService.findUserById(userId);
        if (user == null) {
            return "redirect:/users";
        }
        user.update(userToUpdate);
        return "redirect:/users";
    }

}
