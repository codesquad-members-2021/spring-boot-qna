package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.user.User;

import com.codessquad.qna.web.domain.user.UserRepository;

import com.codessquad.qna.web.exception.UserNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/create")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PutMapping("/{id}/update")
    public String updateProfile(@PathVariable long id, User updatedUser, String oldPassword) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        if(user.isMatchingPassword(oldPassword)){
            user.update(updatedUser);
            userRepository.save(user);
        }
        return "redirect:/users";
    }

}
