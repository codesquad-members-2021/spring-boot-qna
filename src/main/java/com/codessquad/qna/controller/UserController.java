package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.exception.UserNotFoundException;
import com.codessquad.qna.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.Optional;

import static com.codessquad.qna.HttpSessionUtils.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @GetMapping("/login")
    public String loginForm() { return "/user/login"; }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session, Model model) {
        Optional<User> tempUser = userService.findByUserId(userId);

        if (!tempUser.isPresent()) {
            model.addAttribute("errorMessage", "errorMessage");
            return "user/login";
        }

        User user = tempUser.get();
        if (!user.matchPassword(password)) {
            model.addAttribute("errorMessage", "errorMessage");
            return "user/login";
        }

        logger.info("User Logged In: {}", user);
        session.setAttribute(USER_SESSION_KEY, user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
        return "redirect:/";
    }

    @PostMapping
    public String create(User user) {
        userService.save(user);
        logger.info("New User Created: {}", user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String showModifyProfile(@PathVariable long id, Model model, HttpSession session) {
        if (!isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User user = validateUser(id, session);
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @GetMapping("/{id}/wrongPassword")
    public String modifyProfileWithErrMsg(@PathVariable long id, Model model, HttpSession session) {
        if (!isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User user = validateUser(id, session);
        model.addAttribute("errorMessage", "errorMessage");
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PutMapping("/{id}/update")
    public String modifyProfile(@PathVariable long id, User updatedUser, String oldPassword,
                                HttpSession session, Model model) {
        if (!isLoginUser(session)) {
            return "redirect:/users/login";
        }
        User user = validateUser(id, session);

        if (!user.matchPassword(oldPassword)) {
            model.addAttribute("user", user);
            return "redirect:/users/{id}/wrongPassword";
        }

        logger.info("Updated info: {}", updatedUser);
        userService.update(user, updatedUser);
        return "redirect:/users";
    }

    private User validateUser(@PathVariable long id, HttpSession session) {
        User sessionedUser = getUserFromSession(session);
        if (!sessionedUser.matchId(id)) {
            throw new IllegalUserAccessException();
        }
        return userService.findById(id);
    }
}
