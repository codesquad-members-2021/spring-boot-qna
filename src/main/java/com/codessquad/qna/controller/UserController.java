package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/user/list";
    }

    @PostMapping()
    public String account(User user) {
        logger.debug("user: {}", user);
        // TODO: null check before create user
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        Optional<User> user = userService.findUserById(id);
        if (!user.isPresent()) {
            return "redirect:/users";
        }
        model.addAttribute(user.get());
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String form(@PathVariable Long id, Model model) {
        Optional<User> user = userService.findUserById(id);
        if (!user.isPresent()) {
            return "redirect:/users";
        }
        model.addAttribute(user.get());
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User newUser) {
        Optional<User> userOptional = userService.findUserById(id);
        if (!userOptional.isPresent()) {
            return "redirect:/users";
        }
        User user = userOptional.get();
        logger.debug("user : {}", (user));

        user.update(newUser);
        userService.save(user);

        return "redirect:/users";
    }

}
