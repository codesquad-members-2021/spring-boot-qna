package com.codessquad.qna.user;

import com.codessquad.qna.utils.SessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView getUsers() {
        return new ModelAndView("/user/list", "users", userService.getUsers());
    }

    @GetMapping("/{id}")
    public ModelAndView getUser(@PathVariable Long id) {
        return new ModelAndView("/user/profile", "user", userService.getUser(id));
    }

    @PostMapping
    public String createUser(UserDTO user) {
        userService.createUser(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/form")
    public ModelAndView getUserUpdateForm(@PathVariable Long id, HttpSession session) {
        SessionUtils.verifyWithSessionUserId(session, id);

        return new ModelAndView("/user/updateForm", "user", userService.getUser(id));
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, UserDTO newUser, HttpSession session) {
        SessionUtils.setSessionUser(session, userService.updateUser(id, newUser).toEntity());

        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        SessionUtils.setSessionUser(session, userService.getUserWithVerifyPassword(userId, password).toEntity());

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        SessionUtils.removeSessionUser(session);

        return "redirect:/";
    }
}
