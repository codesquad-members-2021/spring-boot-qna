package com.codessquad.qna.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ModelAndView getUsers() {
        return new ModelAndView("/user/list", "users", userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ModelAndView getUser(@PathVariable Long id) {
        User user = userRepository.findById(id).get();
        return new ModelAndView("/user/profile", "user", user);
    }

    @PostMapping
    public String createUser(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/form")
    public ModelAndView getUserUpdateForm(@PathVariable Long id) {
        User user = userRepository.findById(id).get();
        return new ModelAndView("/user/updateForm", "user", user);
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, User newUser) {
        User existedUser = userRepository.findById(id).get();
        userRepository.save(User.of(existedUser, newUser));
        return "redirect:/users";
    }
}
