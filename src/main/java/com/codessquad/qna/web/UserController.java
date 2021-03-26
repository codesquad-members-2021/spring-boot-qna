package com.codessquad.qna.web;

import com.codessquad.qna.domain.Result;
import com.codessquad.qna.service.UserService;
import com.codessquad.qna.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String create(User user) {
        if (user.checkEmpty(user)) {
            return "user/form";
        }
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.getUserList());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String userProfile(@PathVariable long id, Model model, HttpSession session) {
        model.addAttribute("user", userService.getUserById(id));
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String editUser(@PathVariable long id, Model model, HttpSession session) {
        Result result = valid(session);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);

        if (!sessionUser.isMatchingId(id)) {
            model.addAttribute("errorMessage", "수정할 수 있는 권한이 없습니다.");
            return "user/login";
        }

        model.addAttribute("user", sessionUser);

        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable long id, User updateUser, String newPassword) {
        userService.updateUser(id, updateUser, newPassword);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, Model model, HttpSession session) {
        User user = userService.getUserByUserId(userId);

        if (!user.isMatchingPassword(password)) {
            model.addAttribute("errorMessage", "비밀번호를 확인하여 주세요");
            return "user/login";
        }
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

    private Result valid(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("로그인을 먼저 진행해주세요.");
        }
        return Result.ok();
    }

}
