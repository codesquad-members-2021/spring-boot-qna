package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.HttpSessionUtils.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/login")
    public String loginForm() { return "/user/login"; }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userService.findByUserId(userId);

        if (!user.matchPassword(password)) {
            return "redirect:/users/login";
        }

        logger.info("User Logged In: {}", user);
        session.setAttribute(USER_SESSION_KEY, user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
        return "redirect:/";
    }

    @PostMapping
    public String create(User user) {
        userService.save(user);
        logger.info("New User Created: {}", user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String showModifyProfile(@PathVariable long id, Model model, HttpSession session) {
        if (!isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User sessionedUser = getUserFromSession(session);
        if (!sessionedUser.matchId(id)) {
            throw new IllegalArgumentException("User Can Only Change Their Info");
        }
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PutMapping("/{id}/update")
    public String modifyProfile(@PathVariable long id, User updatedUser, String oldPassword, HttpSession session) {
        if (!isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User sessionedUser = getUserFromSession(session);
        if (!sessionedUser.matchId(id)) {
            throw new IllegalArgumentException("User Can Only Change Their Info");
        }

        User user = userService.findById(id);

        if (!user.verifyPassword(oldPassword)) {
            logger.debug("Old Password Does Not Match");
            return "redirect:/users";
        }

        logger.info("Updated info: {}", updatedUser);
        userService.update(user, updatedUser);
        return "redirect:/users";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleException() {
        return "error";
    }
}
