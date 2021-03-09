package com.codessquad.qna.web;

import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/users")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/form")
    public String getUserFormPage() {
        return "user/form";
    }

    @PostMapping("/")
    public String createUser(User user) {
        userRepository.save(user);
        logger.debug("user : {}", user.toString());
        return "redirect:/users/";
    }

    @GetMapping("/")
    public String getUserList(Model model) {
        model.addAttribute("userList", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String getUserProfile(@PathVariable Long id, Model model) {
        Optional<User> user = userRepository.findById(id);

        if(!user.isPresent()) {
            return "redirect:/users/";
        }

        model.addAttribute("user", user.get());
        logger.debug("user : {}", user.toString());

        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String getUpdateForm(@PathVariable Long id, Model model) {
        Optional<User> user = userRepository.findById(id);

        model.addAttribute("user", user.get());
        logger.debug("user : {}", user.toString());

        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String prevPassword, User updateUser) {
        User user = userRepository.findById(id).get();

        if(!user.matchPassword(prevPassword)) {
            return "redirect:/users/";
        }

        user.update(updateUser);
        userRepository.save(user);

        logger.debug("user : {}", updateUser.toString());

        return "redirect:/users/";
    }
}
