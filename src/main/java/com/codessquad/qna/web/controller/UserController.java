package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/loginForm")
    public String getLoginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(User user, HttpSession session) {
        User originUser;
        try {
            originUser = userService.login(user);
        } catch (IllegalStateException e){
            return "redirect:/users/loginForm";
        }
        session.setAttribute("user", originUser);
        return "redirect:/";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }

    @PostMapping
    public String createUser(User user) {
        try {
            userService.signUp(user);
        } catch (IllegalStateException e) {
            return "redirect:/";
        }
        return "redirect:/users";
    }

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", userService.findUsers());
        return "/user/list";
    }

    @GetMapping("/{id}")
    public String getProfile(@PathVariable long id, Model model) {
        try {
            model.addAttribute("user", userService.findUser(id));
        } catch (IllegalStateException e) {
            return "redirect:/";
        }
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String getUpdateForm(@PathVariable long id, Model model) {
        try {
            model.addAttribute("user", userService.findUser(id));
        } catch (IllegalStateException e) {
            return "redirect:/";
        }
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable long id, String testPassword, User user) {
        try {
            userService.updateUser(id, testPassword, user);
        } catch (IllegalStateException e) {
            return "redirect:/";
        }
        return "redirect:/users";
    }
}
