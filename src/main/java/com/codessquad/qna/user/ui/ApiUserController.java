package com.codessquad.qna.user.ui;

import com.codessquad.qna.user.application.UserService;
import com.codessquad.qna.user.dto.UserRequest;
import com.codessquad.qna.user.dto.UserResponse;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static com.codessquad.qna.common.HttpSessionUtils.checkAuthorization;

@RestController
@RequestMapping("/api/users")
public class ApiUserController {
    private final UserService userService;

    public ApiUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse createUser(@Valid UserRequest userRequest) {
        return userService.save(userRequest);
    }
    
    @GetMapping
    public List<UserResponse> getUsers() {
        return userService.getList();
    }

    @GetMapping("{id}")
    public UserResponse get(@PathVariable Long id) {
        return userService.get(id);
    }

    @PutMapping("{id}")
    public UserResponse update(@PathVariable Long id, @Valid UserRequest userRequest, HttpSession session) {
        checkAuthorization(id, session);
        return userService.update(id, userRequest);
    }
}
