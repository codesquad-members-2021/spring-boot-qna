package com.codessquad.qna.controller;

import com.codessquad.qna.exception.EntityNotFoundException;
import com.codessquad.qna.model.User;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.service.UserService;
import com.codessquad.qna.utils.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.utils.HttpSessionUtils.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/userList";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "user/userSignup";
    }

    @PostMapping("/signup")
    public String signup(User user) {
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/loginForm";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        session.setAttribute(USER_SESSION_KEY, userService.login(userId, password));
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String userProfile(@PathVariable Long id, Model model) {
        model.addAttribute("user", findById(id));
        return "user/userProfile";
    }

    @GetMapping("/{id}/updateForm")
    public String updateFormPage(@PathVariable Long id, Model model, HttpSession session) {
        checkSessionedUserId(session, id);
        User user = findById(id);
        model.addAttribute("user", user);
        return "user/userUpdateForm";
    }

    @PutMapping("/{id}/updateForm")
    public String updateUser(@PathVariable Long id, User targetUser, String currentPassword, HttpSession session) {
        checkSessionedUserId(session, id);
        User user = findById(id);
        if (!user.matchPassword(currentPassword)) {
            return "redirect:/users/" + id + "/updateForm";
        }
        user.update(targetUser);
        userRepository.save(user);
        return "redirect:/users";
    }

    private User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));
    }
}

