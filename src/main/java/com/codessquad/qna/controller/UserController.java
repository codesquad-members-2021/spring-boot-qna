package com.codessquad.qna.controller;

import com.codessquad.qna.model.User;
import com.codessquad.qna.repository.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    Users users = new Users();

    @GetMapping("/user/form")
    public String viewForm() {
        logger.info("회원가입 페이지 요청");
        return "user/form";
    }

    @PostMapping("/users")
    public String signUp(User user) {
        User newUser = new User();
        newUser.setUserId(user.getUserId());
        newUser.setPassword(user.getPassword());
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        users.addUser(newUser);
        logger.info("회원가입 요청 " + newUser.toString());
        return "redirect:/users";
    }

}
