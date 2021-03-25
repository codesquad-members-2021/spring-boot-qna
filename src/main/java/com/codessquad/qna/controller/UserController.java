package com.codessquad.qna.controller;

import com.codessquad.qna.exception.EntryNotFoundException;
import com.codessquad.qna.repository.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

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
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String userProfile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(() -> new EntryNotFoundException("유저")));
        return "user/userProfile";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return "redirect:/user/loginForm";
        }
        if (!password.equals(user.getPassword())) {
            return "redirect:/user/loginForm";
        }
        System.out.println("Login Success!");
        session.setAttribute("sessionedUser", user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionedUser");
        System.out.println("Logout Success");
        return "redirect:/";
    }

    @GetMapping("/{id}/updateForm")
    public String updateFormPage(@PathVariable("id") Long id, Model model, HttpSession session) {
        User sessionedUser = (User) session.getAttribute("sessionedUser");
        if (sessionedUser == null || !sessionedUser.matchId(id)) {
            return "redirect:/users/login";
        }
        User user = userRepository.findById(id).orElseThrow(() -> new EntryNotFoundException("유저"));
        model.addAttribute("user", user);
        return "user/userUpdateForm";
    }

    @PutMapping("/{id}/updateForm")
    public String updateUser(@PathVariable("id") Long id, User targetUser, String currentPassword, HttpSession session) {
        User sessionedUser = (User) session.getAttribute("sessionedUser");
        if (sessionedUser == null || !sessionedUser.matchId(id)) {
            return "redirect:/users/login";
        }
        User user = userRepository.findById(id).orElseThrow(() -> new EntryNotFoundException("유저"));
        if (!user.matchPassword(currentPassword)) {
            return "redirect:/users/" + id + "/updateForm";
        }
        user.update(targetUser);
        userRepository.save(user);
        return "redirect:/users";
    }
}

