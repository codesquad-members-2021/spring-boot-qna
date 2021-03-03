package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(UserController.class);

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

    @GetMapping("/{userId}")
    public String renderProfile(@PathVariable String userId, Model model) {
        User getUser = userRepository.findUserById(userId);
        model.addAttribute("user", getUser);
        return "user/profile";
    }

    @GetMapping("/{userId}/form")
    public String renderUpdateForm(@PathVariable String userId, Model model) {
        User getUser = userRepository.findUserById(userId);
        model.addAttribute("user", getUser);
        return "user/userUpdateForm";
    }

    @GetMapping("/update")
    public String userUpdate(User user, String newPassword) {

        if (userRepository.checkPassword(user)) {
            userRepository.updateUserInfo(user, newPassword);
            return "/";
        }

        return "user/userUpdateForm";
    }


}
