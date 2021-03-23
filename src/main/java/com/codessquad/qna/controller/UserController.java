package com.codessquad.qna.controller;

import com.codessquad.qna.repository.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String userList(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
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

    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return "redirect:/user/loginForm";
        }
        if (!password.equals(user.getPassword())) {
            return "redirect:/user/loginForm";
        }
        System.out.println("Login Success!");
        session.setAttribute("user", user);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public ModelAndView userProfile(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("userProfile");
        modelAndView.addObject("user", userRepository.findById(id).get());
        return modelAndView;
    }

    @GetMapping("/{id}/password-check")
    public String passwordCheckPage(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id).orElseGet(User::new);
        model.addAttribute("user", user);
        return "passwordCheckForm";
    }

    @PostMapping("/{id}/password-check")
    public String checkPassword(@PathVariable("id") Long id, User targetUser, Model model) {
        User user = userRepository.findById(id).orElseGet(User::new);
        String passwordBefore = user.getPassword();
        if (passwordBefore.equals(targetUser.getPassword())) {
            model.addAttribute("user", user);
            return "userUpdateForm";
        }
        return "redirect:/";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") Long id, User targetUser) {
        User user = userRepository.findById(id).orElseGet(User::new);
        user.setPassword(targetUser.getPassword());
        user.setName(targetUser.getName());
        user.setEmail(targetUser.getEmail());
        userRepository.save(user);
        return "redirect:/user";
    }
}

