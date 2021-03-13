package com.codessquad.qna.controller;

import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.service.UserService;
import com.codessquad.qna.utils.HttpSessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequestMapping("/users")
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public String CreateUser(User user) {
        userService.create(user);
        return "redirect:/users/";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/users/list";
    }

    @GetMapping("/{id}")
    public String getProfile(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "/users/profile";
    }

    @GetMapping("/{id}/form")
    public String getUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.isYourId(id)) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "/users/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, User userWithUpdatedInfo) {
        userService.update(id, userWithUpdatedInfo);
        return "redirect:/users/";
    }

    @GetMapping("/login")
    public String login() {
        return "/users/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userService.findByUserId(userId);
        if (user == null || !user.isCorrectPassword(password)) {
            return "/users/login_failed";
        }
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }
}
