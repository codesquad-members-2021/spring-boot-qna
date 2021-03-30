package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.user.User;

import com.codessquad.qna.web.dto.user.CreateUserRequest;
import com.codessquad.qna.web.service.UserService;
import com.codessquad.qna.web.utils.SessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userService.login(userId, password, session);
        session.setAttribute("sessionedUser", user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionedUser");
        return "redirect:/";
    }

    @PostMapping
    public String create(@Valid CreateUserRequest request) {
        userService.create(request);
        return "redirect:/users";
    }

    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("users", userService.findAllUser());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String getUpdateForm(@PathVariable long id, HttpSession session, Model model) {
        User loginUser = SessionUtils.getLoginUser(session);
        model.addAttribute("user", userService.verifiedUser(id, loginUser));
        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateProfile(@PathVariable long id, @Valid User updatedUser, String oldPassword, HttpSession session) {
        User loginUser = SessionUtils.getLoginUser(session);
        userService.updateProfile(id, oldPassword, updatedUser, loginUser);
        return "redirect:/users";
    }
}
