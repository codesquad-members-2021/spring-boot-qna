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
    private UserService users = new UserService();

    @PostMapping("/users")
    public String signUp(User user) {
        boolean result = users.addUser(user);
        logger.info("회원가입 요청");
        return result ? "redirect:/users" : "redirect:/user/form";
    }

    @GetMapping("/users")
    public String viewUserList(Model model) {
        model.addAttribute("users", this.users.getAllUser());
        logger.info("유저 리스트 페이지 요청");
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String viewProfile(@PathVariable("userId") String userId, Model model) {
        User user = this.users.findUser(userId);
        model.addAttribute("user", user);
        logger.info("유저 프로필 페이지 요청");
        return (user.getUserId() != null) ? "user/profile" : "redirect:/users";
    }

    @GetMapping("/user/{userId}/form")
    public String viewUpdateProfile(@PathVariable("userId") String userId, Model model) {
        User user = this.users.findUser(userId);
        model.addAttribute("user", user);
        logger.info("유저 정보 수정 페이지 요청");
        return (user.getUserId() != null) ? "user/updateForm" : "redirect:/users";
    }

    @PutMapping("/user/{userId}/update")
    public String updateProfile(@PathVariable("userId") String userId, User user, String newPassword) {
        boolean result = users.updateUser(userId, user, newPassword);
        logger.info("유저 정보 수정 요청");
        return result ? "redirect:/users" : "redirect:/user/" + userId + "/form";
    }

}
