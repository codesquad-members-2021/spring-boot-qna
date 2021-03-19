package com.codesquad.qna.controller;

import com.codesquad.qna.domain.User;
import com.codesquad.qna.exception.UnauthorizedUserAccessException;
import com.codesquad.qna.service.UserService;
import com.codesquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userService.findUserByUserId(userId);

        if (!user.isMatchedPassword(password)) {
            throw new UnauthorizedUserAccessException("Password does not match!");
        }

        logger.debug("User : {} Login Success!", user.getUserId());
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);

        return "redirect:/";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/user/list";
    }

    @PostMapping
    public String create(User user) {
        logger.info("User: {}", user);

        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        User user = userService.findUserByUserId(id);
        model.addAttribute(user);
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        User user = userService.findUserBySession(id, session);
        model.addAttribute(user);
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User updatedUser, String newPassword, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        User user = userService.findUserBySession(id, session);

        if (!user.isMatchedPassword(updatedUser)) {
            logger.debug("Password : \"{}\" does not match \"{}\"", updatedUser.getPassword(), user.getPassword());
            throw new UnauthorizedUserAccessException("Password does not match!");
        }
        userService.update(user, updatedUser, newPassword);

        return "redirect:/users";
    }

}
