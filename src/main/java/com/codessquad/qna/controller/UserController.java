package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/user/profile");
        modelAndView.addObject("user", userRepository.findById(id).orElseThrow(NullPointerException::new));
        return modelAndView;
    }

    @GetMapping("/{id}/form")
    public ModelAndView update(@PathVariable Long id, Model model) {
        ModelAndView modelAndView = new ModelAndView("/user/updateForm");
        modelAndView.addObject("user", userRepository.findById(id).get());
        return modelAndView;
    }

    @PostMapping("/{id}")
    public String updateForm(@PathVariable Long id, User newUser) {
        User user = userRepository.findById(id).get();
        user.update(newUser);
        return "redirect:/users";
    }
}
