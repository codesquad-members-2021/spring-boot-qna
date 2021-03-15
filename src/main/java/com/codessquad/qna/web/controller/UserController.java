package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.user.User;

import com.codessquad.qna.web.domain.user.UserRepository;

import javassist.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) throws NotFoundException {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return "redirect:/users/login-form";
        }
        if (!user.isMatchingPassword(password)) {
            return "redirect:/users/login-form";
        }
        session.setAttribute("sessionedUser", user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionedUser");

        return "redirect:/";
    }

    @PostMapping("/create")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("No user with id number " + id));
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model, HttpSession session) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("No user with id number " + id));
        Object value = session.getAttribute("sessionedUser");

        if (value == null) {
            return "redirect:/users/login-form";
        }
        User sessionedUser = (User) value;
        if (sessionedUser.isMatchingId(id)){
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다");
        }
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PutMapping("/{id}/update")
    public String updateProfile(@PathVariable long id, User updatedUser, String oldPassword) throws NotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("No user with id number " + id));
        if (user.isMatchingPassword(oldPassword)) {
            user.update(updatedUser);
            userRepository.save(user);
        }
        return "redirect:/users";
    }

    private boolean identify(Long id, HttpSession session) {
        Object value = session.getAttribute("sessionedUser");
        if (value == null) {
            return false;
        }
        User sessionedUser = (User) value;
        return (sessionedUser.isMatchingId(id));
    }

}
