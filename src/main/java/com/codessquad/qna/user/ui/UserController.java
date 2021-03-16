package com.codessquad.qna.user.ui;

import com.codessquad.qna.user.application.UserService;
import com.codessquad.qna.user.domain.User;
import com.codessquad.qna.user.dto.UserRequest;
import com.codessquad.qna.user.dto.UserResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        UserResponse userResponse = userService.saveUser(userRequest);
        return ResponseEntity
                .created(URI.create("/users/" + userResponse.getId()))
                .body(userResponse);
    }

    @PostMapping("create")
    public String createUser(User user) {
        userService.saveUser(UserRequest.of(user));
        return "redirect:/users";
    }

    @PostMapping("login")
    public String login(String userId, String password, HttpSession session) {
        User user = userService.login(userId, password);
        session.setAttribute("user", user);
        return "redirect:/";
    }


    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "user/list";
    }

    @GetMapping("{id}")
    public String user_profile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "user/profile";
    }

    @GetMapping("{id}/form")
    public String user_form(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "user/updateForm";
    }

    @PutMapping("{id}")
    public String updateUser(@PathVariable Long id, User user) {
        userService.updateUser(id, UserRequest.of(user));
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
