package com.codessquad.qna.controller;

import com.codessquad.qna.repository.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private List<User> users = new ArrayList<>();

    @GetMapping("/user/new")
    public String toCreateUser() {
        return "signup";
    }

    @PostMapping("/user/new")
    public String createUser(User user) {
        user.setId((long) (users.size() + 1));
        users.add(user);
        return "redirect:/user";
    }

    @GetMapping("/users")
    public String showUser(Model model) {
        model.addAttribute("users", users);
        return "list";
    }

    @GetMapping("/users/{userId}")
    public String showProfile(@PathVariable("userId") String userId, Model model) {
        for (User user : users) {
            if (user.isSameId(userId)) {
                model.addAttribute("user", user);
                break;
            }
        }
        return "profile";
    }

    @GetMapping("/users/{userId}/checkPassword")
    public String checkPassword(@PathVariable("userId") String userId, Model model) {
        model.addAttribute("userId", userId);
        return "checkPassword";
    }

    @PostMapping("/users/{userId}/checkPassword")
    public String passwordRightOrWrong(User user) {
        String userId = user.getUserId();
        String inputPw = user.getPassword();
        for (User aUser : users) {
            if (aUser.isSameId(userId)) {
                String pwBefore = aUser.getPassword();
                if (pwBefore.equals(inputPw))
                    return "updateForm";
            }
        }
        return "redirect:/";
    }

    @GetMapping("/users/{userId}/updateForm")
    public String updateForm(@PathVariable("userId") String userId, Model model) {
        for (User aUser : users) {
            if (aUser.isSameId(userId)) {
                model.addAttribute("user", aUser);
                break;
            }
        }
        return "updateForm";
    }

    @PostMapping("/user/{userId}/update")
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
        return "redirect:/users";
    }
}

