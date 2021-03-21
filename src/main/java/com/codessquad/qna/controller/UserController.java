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
        List<UserDto> getUsers = userService.findAll();
        model.addAttribute("users", getUsers);
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String renderProfile(@PathVariable Long userId, Model model) {
        UserDto user = userService.findByIdToDto(userId);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String renderUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        UserDto user = HttpSessionUtils.getUserFromSession(session);
        userService.checkSameUser(user, id);
        model.addAttribute("user", user);
        return "user/userUpdateForm";
    }

    @PutMapping("/{id}")
    public String userUpdate(@PathVariable Long id, User updatedUser, String newPassword, HttpSession session) {
        UserDto user = HttpSessionUtils.getUserFromSession(session);
        userService.checkSameUser(user, id);
        userService.update(updatedUser, newPassword, id);
        HttpSessionUtils.updateSessionUser(UserDto.createDto(updatedUser), session);
        return "redirect:/";
    }

}
