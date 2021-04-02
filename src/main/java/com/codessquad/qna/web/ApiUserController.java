package com.codessquad.qna.web;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {

    private UserService userService;

    public ApiUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User show(@PathVariable Long id) {
        return userService.getUserById(id);
    }
}
