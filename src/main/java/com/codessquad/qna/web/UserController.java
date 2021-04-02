package com.codessquad.qna.web;

import com.codessquad.qna.domain.Result;
import com.codessquad.qna.service.UserService;
import com.codessquad.qna.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String create(String userId, String password, String name, String email) {
        if (!userService.save(userId, password, name, email)) {
            return "user/form";
        }
        return "redirect:/users";
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String userProfile(@PathVariable long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String editUser(@PathVariable long id, Model model, HttpSession session) {
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        boolean isLoginUser = HttpSessionUtils.isLoginUser(session);

        Result result = userService.valid(id, isLoginUser, sessionUser);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }

        model.addAttribute("user", sessionUser);

        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable long id, String userId, String password, String name, String email, String newPassword) {
        userService.updateUser(id, userId, password, name, email, newPassword);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, Model model, HttpSession session) {
        User user = userService.getUserByUserId(userId);

        Result result = userService.valid(user, password);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
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
