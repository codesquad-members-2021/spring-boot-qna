package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {
    private final String CONFIRM_INFO = "/confirm/{id}";
    private final String UPDATE_INFO = "/update/{id}";

    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public String create(User user) {
        System.out.println("user Info: " + user);
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        System.out.println(model);
        return "user/list";
    }

    @GetMapping("/{id}")
    public String viewProfile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElse(null));
        return "user/profile";
    }

    @GetMapping(CONFIRM_INFO)
    public ModelAndView confirmUserInfo(@PathVariable Long id) {
        return getUserRepository("/user/confirmUserInfo", id);
    }

    @PostMapping(CONFIRM_INFO)
    public String confirmUserInfo(@PathVariable("id") Long id, String password) {
        User user = userRepository.findById(id).orElse(null);
        String userPassword = user.getPassword();
        if (userPassword.equals(password)) {
            return "redirect:/users/update/{id}";
        }
        return "redirect:/users/confirm/{id}";
    }

    @GetMapping(UPDATE_INFO)
    public ModelAndView updateUserInfo(@PathVariable Long id) {
        return getUserRepository("user/updateForm", id);
    }

    @PutMapping(UPDATE_INFO)
    public String updateUserInfo(@PathVariable("id") Long id, String password, String name, String email) {
        User user = userRepository.findById(id).orElse(null);
        user.updateUserInfo(password, name, email);
        userRepository.save(user);
        return "redirect:/users";
    }

    private ModelAndView getUserRepository(String viewName, Long id) {
        ModelAndView modelAndView = new ModelAndView(viewName);
        User user = userRepository.findById(id).orElse(null);
        modelAndView.addObject("userId", user.getUserId());
        return modelAndView;
    }

}
