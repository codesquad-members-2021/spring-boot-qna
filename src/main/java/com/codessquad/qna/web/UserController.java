package com.codessquad.qna.web;

import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.domain.user.HttpSessionUtils.USER_SESSION_KEY;
import static com.codessquad.qna.domain.user.HttpSessionUtils.getUserFromSession;

@Controller
@RequestMapping("/users")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/form")
    public String getUserFormPage() {
        return "user/form";
    }

    @PostMapping("/")
    public String createUser(User user) {
        userService.signUp(user);
        logger.debug("user : {}", user);
        return "redirect:/users/";
    }

    @GetMapping("/")
    public String getUserList(Model model) {
        model.addAttribute("userList", userService.findUsers());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String getUserProfile(@PathVariable Long id, Model model, HttpSession session) {
        User user = getUserFromSession(session);

        if (!user.matchId(id)) {
            throw new IllegalUserAccessException();
        }

        model.addAttribute("user", user);
        logger.debug("user : {}", user);

        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String getUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        User loginedUser = getUserFromSession(session);

        if (!loginedUser.matchId(id)) {
            throw new IllegalUserAccessException();
        }

        User user = userService.findUser(id);
        model.addAttribute("user", user);
        logger.debug("user : {}", user);

        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String prevPassword,
                         User updateUser, HttpSession session) {
        User loginedUser = getUserFromSession(session);

        if (!loginedUser.matchId(id)) {
            throw new IllegalUserAccessException();
        }

        User user = userService.findUser(id);

        if (!user.matchPassword(prevPassword)) {
            return "redirect:/users/";
        }

        userService.update(updateUser);
        session.setAttribute(USER_SESSION_KEY, user);
        logger.debug("user : {}", user);

        return "redirect:/users/";
    }

    @GetMapping("/loginForm")
    public String getLoginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userService.findUser(userId);

        if (!user.matchPassword(password)) {
            logger.error("로그인에 실패하셨습니다.");
            return "user/login_failed";
        }

        logger.debug("login : {}", user);
        session.setAttribute(USER_SESSION_KEY, user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logOut(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
        return "redirect:/";
    }
}
