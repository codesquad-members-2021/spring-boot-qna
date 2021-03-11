package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public String create(User user) {
        logger.debug("user: {}", user);

        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id)
                .orElseThrow(() -> (new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found.")));

        model.addAttribute(user);
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String form(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id)
                .orElseThrow(() -> (new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found.")));

        model.addAttribute(user);
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User newUser) {
        User user = userService.findUserById(id)
                .orElseThrow(() -> (new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found.")));

        logger.debug("user : {}", (user));

        user.update(newUser);
        userService.save(user);

        return "redirect:/users";
    }

}
