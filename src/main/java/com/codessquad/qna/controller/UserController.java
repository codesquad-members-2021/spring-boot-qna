package com.codessquad.qna.controller;

import com.codessquad.qna.entity.User;
import com.codessquad.qna.exception.NotAuthorizedException;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.service.UserService;
import com.codessquad.qna.util.HttpSessionUtils;
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

    @GetMapping("/{id}/profile")
    public String profile(@PathVariable long id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model, HttpSession session) {
        if (HttpSessionUtils.isAuthorized(id, session)) {
            User user = HttpSessionUtils.getUser(session);
            model.addAttribute("user", user);
            return "user/updateForm";
        }
        throw new NotAuthorizedException();
    }

    @PutMapping("/{id}/update")
    public String update(@PathVariable long id, User user, HttpSession session) {
        if (HttpSessionUtils.isAuthorized(id, session)) {
            userService.updateUser(user);
            return "redirect:/users";
        }
        throw new NotAuthorizedException();
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
        logger.debug("login 요청: id={}", user.getUserId());
        try {
            User toLogin = userService.getUser(user.getUserId());
            if (toLogin.verify(user)) {
                session.setAttribute(HttpSessionUtils.USER_KEY, toLogin);
                return "redirect:/";
            }
            return "redirect:/users/login/failed";
        } catch (NotFoundException ex) {
            return "redirect:/users/login/failed";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        logger.debug("logout 요청");
        session.removeAttribute(HttpSessionUtils.USER_KEY);
        return "redirect:/";
    }
}
