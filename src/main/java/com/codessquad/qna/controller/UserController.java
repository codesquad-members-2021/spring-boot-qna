package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "users/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        return "users/profile";
    }


    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        return "users/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id, String checkPassword, User updateUserInfo, Model model) {
        User user = userRepository.findById(id).get();
        if (!user.isMatchingPassword(checkPassword))
            return "redirect:/users/{id}/form";

        if (updateUserInfo.getPassword() == "")
            updateUserInfo.setPassword(user.getPassword());

        user.update(updateUserInfo);
        userRepository.save(user);
        return "redirect:/users";

    }
}
