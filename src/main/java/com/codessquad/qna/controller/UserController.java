package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Result;
import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.service.UserService;
import com.codessquad.qna.utils.HttpSessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequestMapping("/users")
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public String CreateUser(User user) {
        userService.create(user);
        return "redirect:/users/";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/users/list";
    }

    @GetMapping("/{id}")
    public String getProfile(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "/users/profile";
    }

    @GetMapping("/{id}/form")
    public String getUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        Result result = valid(session, id);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/users/login";
        }
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "/users/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, User userWithUpdatedInfo) {
        userService.update(id, userWithUpdatedInfo);
        return "redirect:/users/";
    }

    @GetMapping("/login")
    public String login() {
        return "/users/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, Model model, HttpSession session) {
        User user = userService.findByUserId(userId);
        Result result = valid(user, password);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/users/login";
        }
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

    private Result valid(HttpSession session, Long id) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("로그인이 필요합니다.");
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.isYourId(id)) {
            return Result.fail("자신의 정보만 수정할 수 있습니다.");
        }
        return Result.ok();
    }

    private Result valid(User user, String password) {
        if (user == null || !user.isCorrectPassword(password)) {
            return Result.fail("아이디 또는 비밀번호가 틀립니다. 다시 로그인 해주세요.");
        }
        return Result.ok();
    }
}
