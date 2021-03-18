package com.codessquad.qna.controller;

import com.codessquad.qna.repository.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private List<User> users = new ArrayList<>();

    @GetMapping
    public String showUserList(Model model) {
        model.addAttribute("users", users);
        return "userList";
    }

    @GetMapping("/new")
    public String toSignupPage() {
        return "userSignup";
    }

    @PostMapping("/new")
    public String makeNewUser(User user) {
        user.setId((long) (users.size() + 1));
        users.add(user);
        return "redirect:/user";
    }

    @GetMapping("/{userId}")
    public String showProfile(@PathVariable("userId") String userId, Model model) {
        for (User user : users) {
            if (user.isSameId(userId)) {
                model.addAttribute("user", user);
                break;
            }
        }
        return "userProfile";
    }

    @GetMapping("/{userId}/password-check")
    public String toCheckPasswordPage(@PathVariable("userId") String userId, Model model) {
        model.addAttribute("userId", userId);
        return "passwordCheckForm";
    }

    @PostMapping("/{userId}/password-check")
    public String checkPassword(User targetUser, Model model) {
        for (User user : users) {
            if (user.isSameId(targetUser.getUserId())) {
                String pwBefore = user.getPassword();
                if (pwBefore.equals(targetUser.getPassword())) {
                    model.addAttribute("user", user);
                    return "userUpdateForm";
                }
            }
        }
        return "redirect:/";
    }

    @PostMapping("/{userId}/edit")
    public String updateUser(@PathVariable("userId") String userId, User targetUser) {
        for (User user : users) {
            if (user.isSameId(userId)) {
                user.setPassword(targetUser.getPassword());
                user.setName(targetUser.getName());
                user.setEmail(targetUser.getEmail());
                break;
            }
        }
        return "redirect:/user";
    }
}

