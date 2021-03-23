package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.service.UserService;
import com.codessquad.qna.web.utils.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UsersController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String createUser(User user) {
        userService.createUser(user);
        LOGGER.info("user created : {}", user.getUserId());
        return "redirect:/users";
    }

    @GetMapping
    public String users(Model model) {
        model.addAttribute("users", userService.users());
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String userDetail(@PathVariable("userId") long id, Model model) {
        User foundUser = userService.userDetail(id);
        model.addAttribute("foundUser", foundUser);
        return "user/profile";
    }

    @GetMapping("/modify-form")
    public String modifyForm() {
        return "user/modify-form";
    }

    @PutMapping("/{id}")
    public String modifyUser(@PathVariable long id, String prevPassword, User newUserInfo, HttpSession session) {
        User loginUser = SessionUtil.getLoginUser(session);
        userService.modifyUser(id, prevPassword, newUserInfo, loginUser);
        return "redirect:/users/" + loginUser.getId();
    }

    @PostMapping("/login")
    public String doLogin(String userId, String password, HttpSession session) {
        User loggedInUser = userService.doLogin(userId, password);
        SessionUtil.setLoginUser(session, loggedInUser);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String doLogout(HttpSession session) {
        User loginUser = SessionUtil.getLoginUser(session);
        LOGGER.info("user logout : {}", loginUser.getUserId());
        SessionUtil.removeLoginUser(session);
        return "redirect:/";
    }
}
