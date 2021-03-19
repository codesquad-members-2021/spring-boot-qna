package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.dto.UserDto;
import com.codessquad.qna.service.UserService;
import com.codessquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String createUser(User user) {
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping
    public String renderUserList(Model model) {
        List<User> getUsers = userService.findAll();
        List<UserDto> userDtos = getUsers.stream()
                .map(o -> o.returnDto())
                .collect(Collectors.toList());
        model.addAttribute("users", userDtos);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String renderProfile(@PathVariable Long userId, Model model) {
        User user = userService.findById(userId);
        model.addAttribute("user", user.returnDto());
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String renderUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        User user = HttpSessionUtils.getUserFromSession(session);
        user.checkSameUser(id);
        model.addAttribute("user", user.returnDto());
        return "user/userUpdateForm";
    }

    @PutMapping("/{id}")
    public String userUpdate(@PathVariable Long id, User updatedUser, String newPassword, HttpSession session) {
        User user = HttpSessionUtils.getUserFromSession(session);
        user.checkSameUser(id);
        userService.update(updatedUser, newPassword, id);
        return "redirect:/";
    }

}
