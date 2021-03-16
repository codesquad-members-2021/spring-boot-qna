package com.codessquad.qna.controller;

import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/users")
@RestController
public class ApiUserController {
    private final UserService userService;

    ApiUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{id}")
    public User show(@PathVariable Long id) {
        return userService.findById(id);
    }
}
