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
import java.util.List;
import java.util.Optional;

import static com.codessquad.qna.controller.HttpSessionUtils.*;

@RequestMapping("/users")
@Controller
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userService.findByUserId(userId);
        if (user == null) {
            logger.info("로그인에 실패했습니다.");
            return "redirect:/users/loginForm";
        }
        if (!user.matchPassword(password)) {
            return "redirect:/users/loginForm";
        }
        session.setAttribute(USER_SESSION_KEY, user);
        logger.info("로그인에 성공했습니다.");
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
        return "redirect:/";
    }

    @PostMapping("")
    public String create(User user) {
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        try {
            User user = userService.findUser(id);
            model.addAttribute("user", user);
            return "user/profile";
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
            return "redirect:/users";
        }
    }

    private void checkPermission(Long id, HttpSession session) {
        if (!isLoginUser(session)) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        User loginUser = getSessionUser(session);
        if (!loginUser.matchId(id)) {
            throw new IllegalStateException("수정 및 삭제 권한이 없습니다.");
        }
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        try {
            checkPermission(id, session);
            User user = userService.findUser(id);
            model.addAttribute("user", user);
            return "user/updateForm";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/users";
        }
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User updatedUser, Model model, HttpSession session) {
        try {
            checkPermission(id, session);
            User user = userService.findUser(id);
            user.update(updatedUser);
            userService.join(user);
            return "redirect:/users";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/users";
        }
    }
}

