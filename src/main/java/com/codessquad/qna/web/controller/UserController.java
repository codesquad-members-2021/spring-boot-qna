package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.user.User;

import com.codessquad.qna.web.domain.user.UserRepository;

import javassist.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) throws NotFoundException {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return "redirect:/users/login-form";
        }
        if (!user.isMatchingPassword(password)) {
            return "redirect:/users/login-form";
        }
        session.setAttribute("user", user);
        return "redirect:/";
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
    public String show(@PathVariable long id, Model model) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("No user with id number " + id));
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("No user with id number " + id));
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PutMapping("/{id}/update")
    public String updateProfile(@PathVariable long id, User updatedUser, String oldPassword) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("No user with id number " + id));
        if (user.isMatchingPassword(oldPassword)) {
            user.update(updatedUser);
            userRepository.save(user);
        }
        return "redirect:/users";
    }

}
