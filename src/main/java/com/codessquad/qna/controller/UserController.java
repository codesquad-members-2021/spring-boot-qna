package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public String signUp(User user) {
        userRepository.save(user);
        logger.info(user.toString());
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String viewUserProfile(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("{id}/form")
    public String viewUpdateUserForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PutMapping("{id}/update")
    public String updateUser(@PathVariable Long id, User updateUser) {
        User targetUser = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        if()
        targetUser.update(updateUser);
        userRepository.save(targetUser);
        return "redirect:/users";
    }
}
