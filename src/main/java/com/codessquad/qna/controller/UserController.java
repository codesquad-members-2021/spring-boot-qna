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

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.controller.HttpSessionUtils.USER_SESSION_KEY;
import static com.codessquad.qna.controller.HttpSessionUtils.getUserFromSession;


@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/form")
    public String viewSingUp() {
        logger.info("회원가입 페이지 요청");
        return "user/form";
    }

    @PostMapping("/user/form")
    public String signUp(User user) {
        this.userService.save(user);
        logger.info("회원가입 요청");
        return "redirect:/user/list";
    }

    @GetMapping("/user/login")
    public String viewLogin() {
        logger.info("로그인 페이지 요청");
        return "user/login";
    }

    @PostMapping("/user/login")
    public String login(String userId, String password, HttpSession session) {
        session.setAttribute(USER_SESSION_KEY, this.userService.login(userId, password));
        logger.info("로그인 요청");
        return "redirect:/";
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
        logger.info("로그아웃 요청");
        return "redirect:/";
    }

    @GetMapping("/user/list")
    public String viewUserList(Model model) {
        model.addAttribute("users", this.userService.findAll());
        logger.info("유저 리스트 페이지 요청");
        return "user/list";
    }

    @GetMapping("/user/{userId}/profile")
    public String viewProfile(@PathVariable("userId") String userId, Model model) {
        model.addAttribute("user", this.userService.findByUserId(userId));
        logger.info("유저 프로필 페이지 요청");
        return "user/profile";
    }

    @GetMapping("/user/{id}/form")
    public String viewUpdateProfile(@PathVariable("id") Long id, Model model, HttpSession session) {
        model.addAttribute("user", this.userService.verifyUser(id, getUserFromSession(session)));
        logger.info("유저 정보 수정 페이지 요청");
        return "user/updateForm";
    }

    @PutMapping("/user/{id}/form")
    public String updateProfile(@PathVariable("id") Long id, User user, String oldPassword, HttpSession session) {
        this.userService.update(id, user, oldPassword, getUserFromSession(session));
        logger.info("유저 정보 수정 요청");
        return "redirect:/user/list";
    }

}
