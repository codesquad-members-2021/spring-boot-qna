package com.codessquad.qna.controller;

import com.codessquad.qna.user.User;
import com.codessquad.qna.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping()
    public String userList(Model model) {
        model.addAttribute("users", userRepository.getUserList());
        return "users/list";
    }

    @PostMapping()
    public String registerUser(User user) {
        userRepository.add(user);
        return "redirect:/users";
    }

    @GetMapping("/{userId}")
    public String userProfile(@PathVariable("userId") String userId, Model model) {
        Optional<User> user = userRepository.findUserById(userId);
        if (!user.isPresent()) {
            return "redirect:/users";
        }
        model.addAttribute(user.get());
        return "users/profile";
    }

    @GetMapping("/{userId}/form")
    public String updateUserForm(@PathVariable("userId") String userId, Model model) {
        Optional<User> user = userRepository.findUserById(userId);
        if (!user.isPresent()) {
            return "redirect:/users";
        }
        model.addAttribute(user.get());
        return "users/update_form";
    }

    @PostMapping("/{userId}/update")
    public String updateUser(@PathVariable("userId") String userId, String oldPassword, User newUserInfo) {
        if (userRepository.checkAndUpdate(userId, oldPassword, newUserInfo)) {
            return "redirect:/users/" + userId;
        }
        return "redirect:/users";
    }

}
