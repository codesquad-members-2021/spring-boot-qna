package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/users")
@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public String create(UserForm form) {
        User user = new User();
        user.setUserId(form.getUserId());
        user.setPassword(form.getPassword());
        user.setName(form.getName());
        user.setEmail(form.getEmail());
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        Optional<User> user = userService.findUser(id);
        if (!user.isPresent()) {
            return "redirect:/users";
        }
        model.addAttribute(user.get());
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        Optional<User> user = userService.findUser(id);
        if (!user.isPresent()) {
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User newUser) {
        if (!userService.findUser(id).isPresent()) {
            return "redirect:/users";
        }
        User user = userService.findUser(id).get();
        user.update(newUser);
        userService.join(user);
        return "redirect:/users";
    }

}
