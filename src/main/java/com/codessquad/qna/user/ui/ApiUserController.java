package com.codessquad.qna.user.ui;

import com.codessquad.qna.user.application.UserService;
import com.codessquad.qna.user.dto.UserRequest;
import com.codessquad.qna.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;
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
    public ResponseEntity<UserResponse> createUser(@Valid UserRequest userRequest) {
        UserResponse userResponse = userService.save(userRequest);
        return ResponseEntity.created(
                URI.create("/api/users" + userResponse.getId())
        ).body(userResponse);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok().body(userService.getList());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok().body(userService.get(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid UserRequest userRequest, HttpSession session) {
        checkAuthorization(id, session);
        return ResponseEntity.ok().body(userService.update(id, userRequest));
    }
}
