package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView userList() {
        ModelAndView modelAndView = new ModelAndView("users/list");
        modelAndView.addObject("users", userRepository.findAll());
        return modelAndView;
    }

    @PostMapping()
    public String registerUser(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public ModelAndView userProfile(@PathVariable("id") Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return new ModelAndView("redirect:/users");
        }
        ModelAndView modelAndView = new ModelAndView("users/profile");
        modelAndView.addObject(user.get());
        return modelAndView;
    }

    @GetMapping("/{id}/form")
    public ModelAndView updateUserForm(@PathVariable("id") Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            return new ModelAndView("redirect:/users");
        }
        ModelAndView modelAndView = new ModelAndView("users/update_form");
        modelAndView.addObject(user.get());
        return modelAndView;
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") Long id, String oldPassword, User newUserInfo) {
        Optional<User> optionalUser = userRepository.findById(id);
        optionalUser.ifPresent(user -> checkPasswordAndUpdate(user, oldPassword, newUserInfo));
        return "redirect:/users";
    }

    private void checkPasswordAndUpdate(User user, String oldPassword, User newUserInfo) {
        if (user.isMatchingPassword(oldPassword)) {
            user.update(newUserInfo);
            userRepository.save(user);
        }
    }

}
