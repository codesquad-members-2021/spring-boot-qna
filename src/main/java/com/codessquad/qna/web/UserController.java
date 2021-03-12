package com.codessquad.qna.web;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.inject.Inject;

@Controller
public class UserController {
    private final UserRepository userRepository;

    @Inject
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/")
    public String create(User user) {
        if (user == null) {
            return "redirect:/";
        }
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String getList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/users/{id}")
    public String getOneUserProfile(@PathVariable long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(NullPointerException::new));
        return "user/profile";
    }

    @GetMapping("/{id}")
    public String editUserInfo(@PathVariable long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(NullPointerException::new));
        return "user/updateForm";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable long id, User updateUser) {
        User user = userRepository.findById(id).orElseThrow(NullPointerException::new);
        user.update(updateUser);

        userRepository.save(user);
        return "redirect:/users";
    }

}
