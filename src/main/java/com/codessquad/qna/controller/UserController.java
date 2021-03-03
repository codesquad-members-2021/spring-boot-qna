package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/create")
    public String renderUserCreateForm() {
        return "/user/form";
    }

    @PostMapping("/create")
    public String createUser(User user) {
        userRepository.save(user);
        return "redirect:/user/users";
    }

    @GetMapping("/users")
    public String renderUserList(Model model) {
        List<User> getUsers = userRepository.findAll();
        model.addAttribute("users", getUsers);
        return "user/list";
    }

    @GetMapping("/profile")
    public String renderProfile() {
        return "user/profile";
    }

    @GetMapping("/{userId}")
    public String renderProfileById(@PathVariable String userId, Model model) {
        User getUser = userRepository.findUserById(userId);
        model.addAttribute("user", getUser);
        return "user/profile";
    }
}
