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
    public ModelAndView readAll() {
        return new ModelAndView("/user/list", "users", userService.readAll());
    }

    @GetMapping("/{id}")
    public ModelAndView read(@PathVariable Long id) {
        return new ModelAndView("/user/profile", "user", userService.read(id));
    }

    @PostMapping
    public String create(UserDTO user) {
        userService.createUser(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/form")
    public ModelAndView viewUpdateForm(@PathVariable Long id, HttpSession session) {
        UserDTO result = userService.readVerifiedUser(id, SessionUtils.getSessionUser(session));

        return new ModelAndView("/user/updateForm", "user", result);
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, UserDTO newUser, HttpSession session) {
        UserDTO verifiedUser = userService.readVerifiedUser(id, SessionUtils.getSessionUser(session));

        SessionUtils.setSessionUser(session, userService.update(verifiedUser, newUser));

        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        UserDTO verifiedUser = userService.readPasswordVerifiedUser(userId, password);

        SessionUtils.setSessionUser(session, verifiedUser);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        SessionUtils.removeSessionUser(session);

        return "redirect:/";
    }
}
