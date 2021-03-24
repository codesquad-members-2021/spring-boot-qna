package com.codessquad.qna.controller;

import com.codessquad.qna.exception.UserNotFoundException;
import com.codessquad.qna.repository.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/userList";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "user/userSignup";
    }

    @PostMapping("/signup")
    public String signup(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String userProfile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(UserNotFoundException::new));
        return "user/userProfile";
    }

    @GetMapping("/{id}/password")
    public String passwordCheckPage(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        model.addAttribute("user", user);
        return "user/passwordCheckForm";
    }

    @PostMapping("/{id}/password")
    public String checkPassword(@PathVariable("id") Long id, User targetUser, Model model) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        String passwordBefore = user.getPassword();
        if (passwordBefore.equals(targetUser.getPassword())) {
            model.addAttribute("user", user);
            return "user/userUpdateForm";
        }
        return "redirect:/";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") Long id, User targetUser) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.update(targetUser);
        userRepository.save(user);
        return "redirect:/users";
    }
}

