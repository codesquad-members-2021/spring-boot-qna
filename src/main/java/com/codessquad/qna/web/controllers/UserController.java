package com.codessquad.qna.web.controllers;

import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/login")
    public String getLogin() {
        return "user/login";
    }

    @GetMapping("/user/form")
    public String getForm() {
        return "user/form";
    }

    @PostMapping("/user/form")
    public String createUser(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String getUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable Long id, Model model) {
        User user = findUserById(id);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/users/{id}/form")
    public String updateUserForm(@PathVariable Long id, Model model) {
        User user = findUserById(id);
        model.addAttribute("user", user );
        return  "user/updateForm";
    }

    public User findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 id의 사용자가 존재하지 않습니다."));
        return user;
    }

    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable Long id, User newInfoUser) {
        User user = findUserById(id);
        user.update(newInfoUser);
        userRepository.save(user);
        return "redirect:/users";
    }
}
