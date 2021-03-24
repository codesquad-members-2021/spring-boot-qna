package com.codessquad.qna.controller;

import com.codessquad.qna.exception.UserNotFoundException;
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

    @GetMapping("/login")
    public String loginForm() {
        return "user/loginForm";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return "redirect:/users/login";
        }
        if (!password.equals(user.getPassword())) {
            return "redirect:/users/login";
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

    @GetMapping("/{id}")
    public String userProfile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(UserNotFoundException::new));
        return "user/userProfile";
    }

    @GetMapping("/password")
    public String passwordCheckPage(Model model, HttpSession session) {
        User sessionedUser = (User) session.getAttribute("sessionedUser");
        if (sessionedUser == null) {
            return "loginForm";
        }
        User user = userRepository.findById(sessionedUser.getId()).orElseThrow(UserNotFoundException::new);
        model.addAttribute("user", user);
        return "user/passwordCheckForm";
    }

    @PostMapping("/password")
    public String checkPassword(User targetUser, Model model, HttpSession session) {
        User sessionedUser = (User) session.getAttribute("sessionedUser");
        if (sessionedUser == null) {
            return "loginForm";
        }
        User user = userRepository.findById(sessionedUser.getId()).orElseThrow(UserNotFoundException::new);
        String passwordBefore = user.getPassword();
        if (passwordBefore.equals(targetUser.getPassword())) {
            model.addAttribute("user", user);
            return "user/userUpdateForm";
        }
        return "redirect:/";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") Long id, User targetUser) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.update(targetUser);
        userRepository.save(user);
        return "redirect:/users";
    }
}

