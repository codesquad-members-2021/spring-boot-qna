package com.codessquad.qna.controller;

import com.codessquad.qna.model.dto.UserDto;
import com.codessquad.qna.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.utils.HttpSessionUtils.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/userList";
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "user/userSignup";
    }

    @PostMapping("/signup")
    public String signup(UserDto userDto) {
        userService.save(userDto);
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
        model.addAttribute("user", userService.findById(id));
        return "user/userProfile";
    }

    @GetMapping("/{id}/updateForm")
    public String updateFormPage(@PathVariable Long id, Model model, HttpSession session) {
        model.addAttribute("user", userService.verifyUser(id, getUserDtoFromSession(session)));
        return "user/userUpdateForm";
    }

    @PutMapping("/{id}/updateForm")
    public String updateUser(@PathVariable Long id, UserDto targetUserDto, String currentPassword, HttpSession session) {
        userService.update(id, targetUserDto, currentPassword, getUserDtoFromSession(session));
        return "redirect:/users";
    }
}

