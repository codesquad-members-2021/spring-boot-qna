package com.codessquad.qna.web.controllers;

import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/form")
    public String viewLoginForm() {
        return "user/form";
    }

    @PostMapping
    public String createUser(User user) {
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping
    public String viewUserList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String viewUserProfileById(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateUserForm(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, User newInfoUser) {
        User user = userService.findUserById(id);
        if (userService.isCorrectPassword(user, newInfoUser.getPassword())) {
            user.update(newInfoUser);
            userService.save(user);
            return "redirect:/users";
        }
        return "user/wrongPassword";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String loginUser(String userId, String password, HttpSession session) {
        User user = userService.findByUserId(userId);
        if(user == null) {
            return "redirect:/users/login";
        }
        if(!password.equals(user.getPassword())) {
            return "redirect:/users/login";
        }
        session.setAttribute("sessionedUser", user);
        return "redirect:/";
    }
}
