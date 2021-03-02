package com.codessquad.qna.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/users")
public class UserController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping
    public String getUsers() {
        return "/user/list.html";
    }

    @GetMapping("/form")
    public String getForm() {
        return "/user/form.html";
    }

    @PostMapping
    public String createUser(User user) {
        logger.debug(user.toString());
        return "redirect:/users";
    }
}
