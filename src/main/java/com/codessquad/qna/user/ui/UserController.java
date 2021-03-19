package com.codessquad.qna.user.ui;

import com.codessquad.qna.user.application.UserService;
import com.codessquad.qna.user.domain.User;
import com.codessquad.qna.user.dto.UserRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

import static com.codessquad.qna.common.HttpSessionUtils.USER_SESSION_KEY;
import static com.codessquad.qna.common.HttpSessionUtils.checkAuthorization;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String createUser(User user) {
        userService.save(UserRequest.from(user));
        return "redirect:/users";
    }

    @PostMapping("login")
    public String login(String userId, String password, HttpSession session) {
        session.setAttribute(USER_SESSION_KEY, userService.login(userId, password));
        return "redirect:/";
    }

    // FIXME: 로그아웃은 POST 로 구현해야한다.
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
        session.invalidate();
        return "redirect:/";
    }


    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "user/list";
    }

    @GetMapping("{id}")
    public String getProfile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "user/profile";
    }

    @GetMapping("{id}/form")
    public String getForm(@PathVariable Long id, Model model, HttpSession session) {
        checkAuthorization(id, session);
        model.addAttribute("user", userService.getUser(id));
        return "user/updateForm";
    }

    @PutMapping("{id}")
    public String updateUser(@PathVariable Long id, User user, HttpSession session) {
        checkAuthorization(id, session);
        userService.updateUser(id, UserRequest.from(user));
        return "redirect:/users";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleIllegalArgsException(DataIntegrityViolationException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
