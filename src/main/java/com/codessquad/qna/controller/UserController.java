package com.codessquad.qna.controller;

import com.codessquad.qna.model.User;
import com.codessquad.qna.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String viewUserList(Model model) {
        model.addAttribute("users", this.userService.findAll());
        logger.info("유저 리스트 페이지 요청");
        return "user/list";
    }

    @PostMapping("/users")
    public String signUp(User user) {
        boolean result = userService.save(user);
        logger.info("회원가입 요청");
        return result ? "redirect:/users" : "redirect:/user/form";
    }

    @GetMapping("/users/{id}")
    public String viewProfile(@PathVariable("id") Long id, Model model) {
        User user = this.userService.findById(id);
        model.addAttribute("user", user);
        logger.info("유저 프로필 페이지 요청");
        return (user.getUserId() != null) ? "user/profile" : "redirect:/users";
    }

    @GetMapping("/user/{id}/form")
    public String viewUpdateProfile(@PathVariable("id") Long id, Model model) {
        User user = this.userService.findById(id);
        model.addAttribute("user", user);
        logger.info("유저 정보 수정 페이지 요청");
        return (user.getUserId() != null) ? "user/updateForm" : "redirect:/users";
    }

    @PutMapping("/user/{id}/update")
    public String updateProfile(@PathVariable("id") Long id, User user, String newPassword) {
        boolean result = userService.update(id, user, newPassword);
        logger.info("유저 정보 수정 요청");
        return result ? "redirect:/users" : "redirect:/user/" + id + "/form";
    }

}
