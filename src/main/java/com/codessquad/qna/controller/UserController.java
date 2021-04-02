package com.codessquad.qna.controller;

import com.codessquad.qna.HttpSessionUtils;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.UnauthenticatedException;
import com.codessquad.qna.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String signUp(User user) {
        if (!user.isEmpty() && userService.isNewUser(user)) {
            userService.create(user);
            logger.info(user.toString());
            return "redirect:/users";
        }
        return "user/create_failed";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userService.findUserByUserId(userId);
        if (!user.isMatchingPassword(password)) {
            throw new UnauthenticatedException("로그인하실 수 없습니다.");
        }
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.findUsers());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String viewUserProfile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "user/profile";
    }

    @GetMapping("{id}/form")
    public String viewUpdateUserForm(@PathVariable Long id, Model model, HttpSession session) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.isMatchingId(id)) {
            model.addAttribute("users", userService.findUsers());
            model.addAttribute("error", "자신의 정보만 수정할 수 있습니다.");
            return "user/list";
        }
        model.addAttribute("user", sessionedUser);
        return "user/updateForm";
    }

    @PutMapping("{id}")
    public String updateUser(@PathVariable Long id, Model model, String oldPassword, User updateUser) {
        User targetUser = userService.findUserById(id);
        if (targetUser.isMatchingPassword(oldPassword)) {
            targetUser.update(updateUser);
            userService.create(targetUser);
            return "redirect:/users";
        }
        model.addAttribute("user", targetUser);
        return "user/update_failed";
    }
}
