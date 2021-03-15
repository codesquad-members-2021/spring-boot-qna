package com.codessquad.qna.web;

import com.codessquad.qna.exception.NoUserException;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(NoUserException::new));
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String update(@PathVariable Long id, Model model, HttpSession session) {
        User sessionedUser = (User) session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
        if (sessionedUser == null) {
            return "redirect:/users/form";
        }
        // Todo: IllegalStateException이랑 NoUserException 예외처리는 둘중 하나만 하면 될듯
        if (!id.equals(sessionedUser.getId())) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }
        model.addAttribute("user", userRepository.findById(id).orElseThrow(NoUserException::new));
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateForm(@PathVariable Long id, String inputPassword, User updatedUser, HttpSession session) {
        User sessionedUser = (User) session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
        if (sessionedUser == null) {
            return "redirect:/users/form";
        }
        // Todo: IllegalStateException이랑 NoUserException 예외처리는 둘중 하나만 하면 될듯
        if (!id.equals(sessionedUser.getId())) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }

        User user = userRepository.findById(id).orElseThrow(NoUserException::new);
        if (user.isPasswordMatching(inputPassword)) {
            user.update(updatedUser);
            return "redirect:/users";
        }
        return "redirect:/users/{id}/form";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId).orElseThrow(NoUserException::new);
        if (!password.equals(user.getPassword())) {
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

    @ExceptionHandler({NoUserException.class, IllegalStateException.class})
    public String handleException() {
        return "exceptionHandle";
    }
}
