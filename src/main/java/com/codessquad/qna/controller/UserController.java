package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(QuestionController.class);
    private List<User> users = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public String signUp(User user) {
        userRepository.save(user);
        logger.info(user.toString());
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String viewUserProfile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "user/profile";
    }

    @GetMapping("{id}/form")
    public String viewUpdateUserForm(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("user", userRepository.findById(id).get());
            return "user/updateForm";
        } catch (IndexOutOfBoundsException e) {
            return "redircet:/users";
        }
    }

    @PostMapping("{id}/update")
    public String updateUser(@PathVariable int id, User updateUser) {
        try {
            User targetUser = users.get(id - 1);
            targetUser.setUser(updateUser);
            return "redirect:/users";
        }
        catch (IndexOutOfBoundsException e){
            return "redirect:/{id}/form";
        }
    }
}
