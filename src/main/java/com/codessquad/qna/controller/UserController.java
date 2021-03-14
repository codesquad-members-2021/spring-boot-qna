package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public String create(User user) {
        userRepository.save(user);
        logger.info("New User Created: {}", user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable long id, Model model) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return "user/list";
        }
        model.addAttribute("user", user.get());
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String showModifyProfile(@PathVariable long id, Model model) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return "redirect:/users";
        }
        model.addAttribute("user", user.get());
        return "user/updateForm";
    }

    @PutMapping("/{id}/update")
    public String modifyProfile(@PathVariable long id, User updatedUser, String oldPassword) {
        User user = userRepository.findById(id).get();
        if (!user.verifyPassword(oldPassword)) {
            logger.debug("Old Password Does Not Match");
            return "redirect:/users";
        }
        logger.info("Updated info: {}", updatedUser);
        userRepository.save(user.updateProfile(updatedUser));
        return "redirect:/users";
    }
}
