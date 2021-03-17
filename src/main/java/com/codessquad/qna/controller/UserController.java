package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static com.codessquad.qna.controller.HttpSessionUtils.*;

@RequestMapping("/users")
@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userService.findByUserId(userId);
        if (!user.isValidPassword(password)) {
            throw new IllegalUserAccessException("비밀번호가 틀렸습니다.");
        }
        session.setAttribute(USER_SESSION_KEY, user);
        logger.debug("user : {}님이 로그인하셨습니다.", user.getUserId());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        logger.debug("user : {}님이 로그아웃하셨습니다.", getSessionUser(session).getUserId());
        session.removeAttribute(USER_SESSION_KEY);
        return "redirect:/";
    }

    @PostMapping
    public String create(User user) {
        userService.join(user);
        logger.debug("user : {}님이 가입하셨습니다.", user.getUserId());
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        User user = userService.findUser(id);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        User user = userService.findVerifiedUser(id, session);
        model.addAttribute("user", user);
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, @Valid User updatedUser, Errors errors, @RequestParam String password, Model model, HttpSession session) {
        User user = userService.findVerifiedUser(id, session);
        updatedUser.setId(id);
        if (errors.hasErrors()) {
            model.addAttribute("user", updatedUser);
            model.addAttribute("errorMessage", Objects.requireNonNull(errors.getFieldError()).getDefaultMessage());
            return "/user/updateForm";
        }
        if (!user.isValidPassword(password)) {
            model.addAttribute("user", updatedUser);
            model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
            return "/user/updateForm";
        }
        userService.update(user, updatedUser);
        session.setAttribute(USER_SESSION_KEY, user);
        return "redirect:/users";
    }
}



