package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();

    @PostMapping("/users")
    public String createUser(User user) {
        setIndex(user);
        users.add(user);
        return "redirect:/users";
    }

    private void setIndex(User user) {
        int index = users.size() + 1;
        user.setIndex(index);
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/users/{userId}")
    public String getProfile(@PathVariable String userId, Model model) {
        Optional<User> foundUser = findByUserId(userId);
        if (foundUser.isPresent()) {
            model.addAttribute("user", foundUser.get());
            return "/user/profile";
        }
        return "redirect:/";
    }

    private Optional<User> findByUserId(String userId) {
        return users.stream()
                .filter(user -> user.getUserId()
                        .equals(userId)).findAny();
    }

    @GetMapping("/users/{index}/form")
    public String getUpdateForm(@PathVariable int index, Model model) {
        try {
            model.addAttribute("user", users.get(index - 1));
        } catch (IndexOutOfBoundsException e) {
            return "redirect:/";
        }
        return "/user/updateForm";
    }

    @PostMapping("/users/{index}/update")
    public String updateUser(User updatedUser) {
        try {
            users.set(updatedUser.getIndex() - 1, updatedUser);
        } catch (IndexOutOfBoundsException e) {
            return "redirect:/";
        }
        return "redirect:/users";
    }
}
