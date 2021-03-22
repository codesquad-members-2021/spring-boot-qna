package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Result;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.LoginFailedException;
import com.codessquad.qna.service.UserService;
import com.codessquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.util.HttpSessionUtils.USER_SESSION_KEY;

@Controller
@RequestMapping("/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String createUser(User newUser, Model model) {

        if (newUser == null) {
            model.addAttribute("errorMessage", "회원가입에 실패하였습니다.");
            return "user/form";
        }

        if (newUser.isEmpty()) {
            model.addAttribute("errorMessage", "회원가입 필드가 누락되었습니다.");
            return "user/form";
        }

        if (userService.isRedundantUser(newUser)) {
            model.addAttribute("errorMessage", "이미 존재하는 회원입니다.");
            return "user/form";
        }

        User savedUser = userService.join(newUser);

        if (!savedUser.equals(newUser)) {
            model.addAttribute("errorMessage", "회원가입에 실패하였습니다.");
            return "user/form";
        }

        return "redirect:/users";
    }

    @GetMapping
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());

        return "user/list";
    }

    @GetMapping("/{id}")
    public String showUserInDetail(@PathVariable long id, Model model, HttpSession session) {
        User user = userService.getOneById(id).orElse(null);
        Result result = checkSession(session, user);

        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }

        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String moveToUpdateForm(@PathVariable long id, Model model, HttpSession session) {
        User user = userService.getOneById(id).orElse(null);
        Result result = checkSession(session, user);

        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }

        model.addAttribute("id", user.getId());
        model.addAttribute("userId", user.getUserId());

        return "user/update";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable long id, User referenceUser, String newPassword, HttpSession session, Model model) {
        User user = userService.getOneById(id).orElse(null);
        Result result = checkSession(session, user);

        if (user == null) {
            model.addAttribute("errorMessage", "존재하지 않는 회원입니다.");
            model.addAttribute("userId", user.getUserId());
            return "user/update";
        }
        if (referenceUser.isEmpty()) {
            model.addAttribute("errorMessage", "비어있는 필드가 있습니다.");
            model.addAttribute("userId", user.getUserId());
            return "user/update";
        }

        if (!user.isEqualPassword(referenceUser.getPassword())) {
            model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
            model.addAttribute("userId", user.getUserId());
            return "user/update";
        }

        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "user/login";
        }

        userService.updateInfo(user, referenceUser, newPassword);
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session, Model model) {
        User user = userService.getOneByUserId(userId)
                .orElseThrow(() -> new LoginFailedException("존재하지 않는 회원입니다."));

//        if (user == null) {
//            model.addAttribute("errorMessage", "존재하지 않는 회원입니다.");
//            model.addAttribute("userId", userId);
//            return "user/login";
//        }
//
        if (!user.isEqualPassword(password)) {
            throw new LoginFailedException("비밀번호가 일치하지 않습니다.");
        }

        session.setAttribute(USER_SESSION_KEY, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);

        return "redirect:/";
    }

    private Result checkSession(HttpSession session, User user) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("로그인이 필요합니다.");
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        if (!user.equals(sessionUser)) {
            return Result.fail("자신만이 사용자의 정보를 확인 및 수정할 수 있습니다.");
        }

        return Result.ok();
    }
}
