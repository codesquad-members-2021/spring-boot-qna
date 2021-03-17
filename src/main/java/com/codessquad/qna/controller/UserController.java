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
    public String showUser(Model model) {
        model.addAttribute("users", users);
        return "list";
    }

    @GetMapping("/new")
    public String toCreateUser() {
        return "signup";
    }

    @PostMapping("/new")
    public String createUser(User user) {
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
        return "profile";
    }

    @GetMapping("/{userId}/password-check")
    public String checkPassword(@PathVariable("userId") String userId, Model model) {
        model.addAttribute("userId", userId);
        return "checkPassword";
    }

    @PostMapping("/{userId}/password-check")
    public String passwordRightOrWrong(User user, Model model) {
        String userId = user.getUserId();
        String inputPw = user.getPassword();
        for (User aUser : users) {
            if (aUser.isSameId(userId)) {
                String pwBefore = aUser.getPassword();
                if (pwBefore.equals(inputPw)) {
                    model.addAttribute("user", aUser);
                    return "updateForm";
                }
            }
        }
        return "redirect:/";
    }

    @PostMapping("/{userId}/edit")
    public String updateUser(@PathVariable("userId") String userId, User user) {
        Long id = 0L;
        for (User aUser : users) {
            if (aUser.isSameId(userId)) {
                id = aUser.getId();
                users.remove(aUser);
                break;
            }
        }
        user.setId(id);
        users.add(user);
        return "redirect:/user";
    }
}

