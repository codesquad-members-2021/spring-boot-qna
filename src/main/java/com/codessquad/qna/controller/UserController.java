package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public String create(User user) {
        userRepository.save(user);
        logger.debug("createUser : {}", user.toString());
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No such data"));
        model.addAttribute("user", user);
        return "users/profile";
    }


    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No such data"));
        model.addAttribute("user", user);
        return "users/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, String checkPassword, User updateUserInfo, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No such data"));
        if (!user.isMatchingPassword(checkPassword))
            return "redirect:/users/{id}/form";

        if (updateUserInfo.getPassword() == "")
            updateUserInfo.setPassword(user.getPassword());

        user.update(updateUserInfo);
        userRepository.save(user);
        return "redirect:/users";

    }
}
