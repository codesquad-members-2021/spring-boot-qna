package com.codessquad.qna.web;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class.getName());

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            LOGGER.info("Login Failure!");
            return "redirect:/users/loginForm";
        }

        if (!password.equals(user.getPassword())) {
            return "redirect:/users/loginForm";
        }

        LOGGER.info("Login Success!");
        session.setAttribute("user", user);

        return "redirect:/";
    }

    @GetMapping("/form")
    public String userForm() {
        return "/user/form";
    }

    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

    @PostMapping("")
    public String createUser(User user) {
        LOGGER.info(user.toString());
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("userList", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        model.addAttribute("user", getUserBy(id));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String getForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", getUserBy(id));
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateForm(@PathVariable Long id, User updatedUser) {
        User user = getUserBy(id);
        user.update(updatedUser);
        userRepository.save(user);
        return "redirect:/users";
    }

    private User getUserBy(Long id) {
        return userRepository.findById(id).get();
    }

}
