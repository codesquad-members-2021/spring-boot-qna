package com.codessquad.qna.controller;

import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.UserIdNotFoundException;
import com.codessquad.qna.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "user/list";
    }

    @PostMapping
    public String create(User user) {
        userService.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}/profile")
    public String profile(@PathVariable String userId, Model model) {
        User user = userService.getUser(userId);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{userId}/form")
    public String updateForm(@PathVariable String userId, Model model) {
        User user = userService.getUser(userId);
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PutMapping("/{userId}/update")
    public String update(User user) {
        userService.updateUser(user);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String loginForm() {
        logger.debug("loginForm을 보여줍니다.");
        return "user/login";
    }

    @GetMapping("/login/failed")
    public String loginFailForm() {
        return "user/login_failed";
    }

    @PostMapping("/login")
    public String login(User user, HttpSession session) {
        logger.debug("login 요청: id={}, password={}", user.getUserId(), user.getPassword());
        try {
            User toLogin = userService.getUser(user.getUserId());
            if (toLogin.verify(user)) {
                session.setAttribute("sessionedUser", toLogin);
                return "redirect:/";
            }
            return "redirect:/users/login/failed";
        } catch (UserIdNotFoundException ex) {
            return "redirect:/users/login/failed";
        }
    }
}
