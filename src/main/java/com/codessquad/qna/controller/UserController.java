package com.codessquad.qna.controller;

import com.codessquad.qna.model.User;
import com.codessquad.qna.repository.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    Users users = new Users();

    @PostMapping("/users")
    public String signUp(User user) {
        users.addUser(user);
        logger.info("회원가입 요청");
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String viewUserList(Model model) {
        model.addAttribute("users", this.users.getAllUser());
        logger.info("유저 리스트 페이지 요청");
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String viewProfile(@PathVariable("userId") String userId, Model model) {
        model.addAttribute("user", this.users.findUser(userId));
        logger.info("유저 프로필 페이지 요청");
        return "user/profile";
    }

}
