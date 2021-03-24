package com.codessquad.qna.controller;

import com.codessquad.qna.exception.UserNotFoundException;
import com.codessquad.qna.repository.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "userList";
    }

    @GetMapping("/new")
    public String signupPage() {
        return "userSignup";
    }

    @PostMapping
    public String signup(User user) {
        userRepository.save(user);
        return "redirect:/user";
    }

    @GetMapping("/{id}")
    public ModelAndView userProfile(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("userProfile");
        modelAndView.addObject("user", userRepository.findById(id).orElseThrow(UserNotFoundException::new));
        return modelAndView;
    }

    @GetMapping("/{id}/password-check")
    public String passwordCheckPage(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        model.addAttribute("user", user);
        return "passwordCheckForm";
    }

    @PostMapping("/{id}/password-check")
    public String checkPassword(@PathVariable("id") Long id, User targetUser, Model model) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        String passwordBefore = user.getPassword();
        if (passwordBefore.equals(targetUser.getPassword())) {
            model.addAttribute("user", user);
            return "userUpdateForm";
        }
        return "redirect:/";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") Long id, User targetUser) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.update(targetUser);
        userRepository.save(user);
        return "redirect:/user";
    }
}

