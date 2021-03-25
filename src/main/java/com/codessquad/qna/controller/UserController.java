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

    @GetMapping("/login")
    public String loginForm() {
        return "user/loginForm";
    }

    @PostMapping("/login")
    public String login(String userId, String password, Model model, HttpSession session) {
        User user = userRepository.findByUserId(userId);
        if (user == null || !user.matchPassword(password)) {
            model.addAttribute("errorMessage", "아이디 또는 비밀번호가 틀립니다. 다시 로그인 해주세요.");
            return "user/loginForm";
        }
        session.setAttribute("sessionedUser", user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionedUser");
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String userProfile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(() -> new EntryNotFoundException("유저")));
        return "user/userProfile";
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

