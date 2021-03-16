package com.codessquad.qna.web;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.isIdMatching(id)) {
            throw new IllegalStateException("자신의 정보만 확인할 수 있습니다.");
        }

        model.addAttribute("user", userRepository.findById(id).orElseThrow(NoSuchElementException::new));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String update(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.isIdMatching(id)) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }
        model.addAttribute("user", userRepository.findById(id).orElseThrow(NoSuchElementException::new));
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateForm(@PathVariable Long id, String inputPassword, User updatedUser, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.isIdMatching(id)) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }
        if (sessionedUser.isPasswordMatching(inputPassword)) {
            sessionedUser.update(updatedUser);
            userRepository.save(sessionedUser);
            return "redirect:/users";
        }
        return "redirect:/users/{id}/form";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);
        if (!user.isPasswordMatching(password)) {
            return "redirect:/users/loginForm";
        }
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException() {
        return "noSuchElementExceptionHandle";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException() {
        return "unableToAccessToOthers";
    }
}
