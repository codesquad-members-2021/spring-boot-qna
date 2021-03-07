package com.codessquad.qna.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private UserRepository userRepository = new UserRepository();

    @PostMapping("/user/create")
    public String user_create(User user) {
        userRepository.add(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String user_list(Model model) {
        model.addAttribute("users", userRepository.getUsers());
        return "user/list";
    }

    @GetMapping("/users/{userId}")
    public String user_profile(@PathVariable("userId") String userId, Model model) {
        model.addAttribute("user", userRepository.getUser(userId));
        return "user/profile";
    }

    @GetMapping("/users/{userId}/form")
    public String user_form(@PathVariable("userId") String userId, Model model) {
        model.addAttribute("user", userRepository.getUser(userId));
        return "user/updateForm";
    }

    @PostMapping("/users/{userId}/update")
    public String user_update(@PathVariable("userId") String userId, User user) {
        userRepository.update(userId, user);
        return "redirect:/users";
    }
}
