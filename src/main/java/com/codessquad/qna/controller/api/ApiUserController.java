package com.codessquad.qna.controller.api;

import com.codessquad.qna.domain.dto.UserDto;
import com.codessquad.qna.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {

    private final UserService userService;

    public ApiUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public UserDto show(@PathVariable Long id) {
        return userService.findByIdToDto(id);
    }
}
