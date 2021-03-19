package com.codessquad.qna.user.ui;

import com.codessquad.qna.user.application.UserService;
import com.codessquad.qna.user.domain.User;
import com.codessquad.qna.user.dto.UserRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.codessquad.qna.common.HttpSessionUtils.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String createUser(@Valid UserRequest userRequest) {
        userService.save(userRequest);
        return "redirect:/users";
    }

    @PostMapping("login")
    public String login(String userId, String password, HttpSession session) {
        User user = userService.login(userId, password);
        setUserAttribute(user, session);
        return "redirect:/";
    }

    // FIXME: 로그아웃은 POST 로 구현해야한다.
    @GetMapping("logout")
    public String logout(HttpSession session) {
        clearSession(session);
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
    public String updateUser(@PathVariable Long id, @Valid UserRequest userRequest, HttpSession session) {
        checkAuthorization(id, session);
        userService.updateUser(id, userRequest);
        return "redirect:/users";
    }
}
