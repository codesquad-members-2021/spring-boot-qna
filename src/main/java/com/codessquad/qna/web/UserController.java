package com.codessquad.qna.web;

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

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class.getName());

    private List<User> userList = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/form")
    public String userForm() {
        return "/user/form";
    }

    @GetMapping("/user/login")
    public String login() {
        return "/user/login";
    }

    @PostMapping("/user/create")
    public String createUser(User user) {
        LOGGER.info(user.toString());
        userRepository.save(user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String list(Model model) {
        model.addAttribute("userList", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/userList/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        searchUserId(userId, model);
        return "/qna/profile";
    }

    @GetMapping("/userList/{userId}/update")
    public String updateUser(@PathVariable String userId, Model model) {
        searchUserId(userId, model);
        return "/user/updateForm";
    }

    private void searchUserId(String userId, Model model) {
        for (User user : userList) {
            if (user.getUserId().equals(userId)) {
                model.addAttribute("user", user);
            }
        }
    }
}
