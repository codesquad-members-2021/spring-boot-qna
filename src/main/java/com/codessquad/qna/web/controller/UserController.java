package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private List<User> users = Collections.synchronizedList(new ArrayList<>());

    @PostMapping
    public String createUser(User user) {
        users.add(user);
        user.setIndex(users.indexOf(user) + 1);
        return "redirect:/users";
    }

    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", users);
        return "/user/list";
    }

    @GetMapping("/{userId}")
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

    @GetMapping("/{index}/form")
    public String getUpdateForm(@PathVariable int index, Model model) {
        try {
            model.addAttribute("user", users.get(index - 1));
        } catch (IndexOutOfBoundsException e) {
            return "redirect:/";
        }
        return "/user/updateForm";
    }

    @PostMapping("/{index}/update")
    public String updateUser(User user) {
        User originUser = users.get(user.getIndex() - 1);
        if(!user.getPassword().equals(originUser.getPassword())) {
            return "redirect:/";
        }
        user.setUserId(originUser.getUserId());
        try {
            users.set(user.getIndex() - 1, user);
        } catch (IndexOutOfBoundsException e) {
            return "redirect:/";
        }
        return "redirect:/users";
    }
}
