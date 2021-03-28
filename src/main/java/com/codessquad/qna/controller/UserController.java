package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.utils.SessionUtil.getLoginUser;
import static com.codessquad.qna.utils.SessionUtil.removeLoginUser;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUserList() {
        return "redirect:/user/list";
    }

    @GetMapping("/form")
    public String signUpForm() {
        logger.info("signUpForm >> users/form.html: in");
        return "user/form";
    }

    @PostMapping("/create")
    public String create(User user) {
        userService.createUser(user);
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("userlist", userService.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String showProfile(@PathVariable Long id, Model model) {
        User user = userService.showProfile(id);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String pastPassword, User updatedUser, HttpSession session) {
        userService.updateUser(id, pastPassword, updatedUser, session);
        return "redirect:/user";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        userService.validationCheck(id, session);
        User loginUser = getLoginUser(session);
        model.addAttribute("user", loginUser);
        return "user/updateForm";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String loginProcess(String userId, String password, HttpSession session) {
        userService.login(userId, password, session);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        logger.info("Logout Out Success");
        removeLoginUser(session);
        return "redirect:/";
    }

}
