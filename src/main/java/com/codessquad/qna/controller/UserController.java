package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

import java.util.ArrayList;
import java.util.List;

@HandlebarsHelper
@Controller
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    private List<User> users = new ArrayList<>();

    @GetMapping("user/form")
    public String getSignUpPage() {
        return "/user/form";
    }

    @PostMapping("user/signUp")
    public String signUp(User user) {
        users.add(user);
        logger.info(user.toString());

        return "redirect:list";
    }

    @GetMapping("user/list")
    public String list(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }
}
