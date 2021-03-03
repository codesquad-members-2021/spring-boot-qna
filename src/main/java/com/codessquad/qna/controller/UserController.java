package com.codessquad.qna.controller;

import com.codessquad.qna.user.UpdateUserRequest;
import com.codessquad.qna.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final List<User> users = new ArrayList<>();

    @GetMapping()
    public String userList(Model model) {
        model.addAttribute("users", users);
        return "users/list";
    }

    @PostMapping()
    public String registerUser(User user) {
        users.add(user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String userProfile(@PathVariable("userId") String userId, Model model) {
        Optional<User> user = findUserById(userId);
        if (!user.isPresent()) {
            return "redirect:/users";
        }
        model.addAttribute(user.get());
        return "users/profile";
    }

    @GetMapping("/{userId}/form")
    public String updateUserForm(@PathVariable("userId") String userId, Model model) {
        Optional<User> optionalUser = findUserById(userId);
        if (!optionalUser.isPresent()) {
            return "redirect:/users";
        }
        User user = optionalUser.get();
        model.addAttribute(user);
        return "users/update_form";
    }

    @PostMapping("/{userId}/update")
    public String updateUser(@PathVariable("userId") String userId, UpdateUserRequest newUserInfo) {
        Optional<User> optionalUser = findUserById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            checkAndUpdate(user, newUserInfo);
        }
        return "redirect:/users";
    }

    private void checkAndUpdate(User user, UpdateUserRequest newUserInfo) {
        String userId = user.getUserId();
        String oldPassword = user.getPassword();
        if (!userId.equals(newUserInfo.getUserId()) || !oldPassword.equals(newUserInfo.getOldPassword())) {
            return;
        }
        user.setPassword(newUserInfo.getNewPassword());
        user.setName(newUserInfo.getName());
        user.setEmail(newUserInfo.getEmail());
    }

    private Optional<User> findUserById(String userId) {
        for (User user : users) {
            if (userId.equals(user.getUserId())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }


}
