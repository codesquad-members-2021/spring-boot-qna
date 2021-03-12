package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.UserService;
import com.codessquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String createUser(User user) {
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping
    public String renderUserList(Model model) {
        List<User> getUsers = userService.findAll();
        model.addAttribute("users", getUsers);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String renderProfile(@PathVariable Long userId, Model model) {
        User findUser = userService.findById(userId);
        model.addAttribute("user", findUser);
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String renderUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!userService.checkSession(session, id)) {
            return "redirect:/users/loginForm";
        }
        model.addAttribute("user", HttpSessionUtils.getUserFromSession(session));
        return "user/userUpdateForm";
    }

    @PutMapping("/{id}")
    public String userUpdate(User user, String newPassword, Model model, HttpSession session) {
        if (!userService.checkSession(session, user.getId())) {
            return "redirect:/users/loginForm";
        }
        if (userService.update(user, newPassword)) {
            return "redirect:/";
        }
        model.addAttribute("fail", true);
        return "user/userUpdateForm";
    }

}
