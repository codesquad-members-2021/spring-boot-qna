package com.codessquad.qna.web.controllers;

import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exception.LoginException;
import com.codessquad.qna.web.exception.UserException;
import com.codessquad.qna.web.service.UserService;
import com.codessquad.qna.web.utility.SessionUtility;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/form")
    public String viewLoginForm() {
        return "user/form";
    }

    @PostMapping
    public String createUser(User user) {
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping
    public String viewUserList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String viewUserProfileById(@PathVariable Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateUserForm(@PathVariable Long id, Model model, HttpSession session) {
        User user = userService.findUserById(id);
        User sessionedUser = SessionUtility.findSessionedUser(session);

        SessionUtility.verifySessionUser(sessionedUser, user);
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, User newInfoUser) {
        User user = userService.findUserById(id);
        if (!userService.isCorrectPassword(user, newInfoUser)) {
            throw new UserException("비밀번호가 틀렸습니다.");
        }
        user.update(newInfoUser);
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String loginUser(String userId, String password, HttpSession session) {
        User user = userService.findByUserId(userId);
        if(user == null) {
            throw new LoginException("해당 userId의 사용자가 존재하지 않습니다.");
        }
        if(!user.matchesPassword(password)) {
            throw new LoginException("비밀번호가 틀렸습니다.");
        }
        session.setAttribute("sessionedUser", user);
        return "redirect:/";
    }
}
