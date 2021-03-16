package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
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
        userService.create(user);
        logger.info(user.toString());
        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userService.findUserByUserId(userId);
        if (user.isMatchingPassword(password)) {
            session.setAttribute("userSession", user);
            return "redirect:/";
        }

        return "redirect:/users/loginForm";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userSession");
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
        Object tempUser = session.getAttribute("userSession");
        if (tempUser == null)
            return "redirect:/users/loginForm";
        User sessionUser = (User)tempUser;
        if(!id.equals(sessionUser.getId())){
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }
        model.addAttribute("user", userService.findUserById(id));
        return "user/updateForm";
    }

    @PutMapping("{id}/update")
    public String updateUser(@PathVariable Long id, String oldPassword, User updateUser, HttpSession session) {
        Object tempUser = session.getAttribute("userSession");
        if (tempUser == null)
            return "redirect:/users/loginForm";
        User sessionUser = (User)tempUser;
        if(!id.equals(sessionUser.getId())){
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }
        User targetUser = userService.findUserById(id);
        if (targetUser.isMatchingPassword(oldPassword)) {
            targetUser.update(updateUser);
            userService.create(targetUser);
            return "redirect:/users";
        }
        return "redirect:/users/{id}/form";
    }
}
