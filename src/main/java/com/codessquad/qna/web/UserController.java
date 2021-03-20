package com.codessquad.qna.web;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import com.codessquad.qna.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.web.HttpSessionUtils.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String create(User user) {
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping
    public String list(Model model) {
        userService.showUserList(model);
        return "user/list";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userService.findUserByUserId(userId);
        userService.checkValidByPassword(user, password);
        session.setAttribute(USER_SESSION_KEY, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model, HttpSession session) {
        User loggedinUser = getUserFromSession(session);
        userService.checkValidById(loggedinUser, id);
        model.addAttribute("user", loggedinUser);
        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String update(@PathVariable Long id, Model model, HttpSession session) {
        User loggedinUser = getUserFromSession(session);
        userService.checkValidById(loggedinUser, id);
        model.addAttribute("user", loggedinUser);
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateForm(@PathVariable Long id, String inputPassword, User updatedUser, HttpSession session) {
        User loggedinUser = getUserFromSession(session);
        userService.checkValidById(loggedinUser, id);
        userService.update(inputPassword, loggedinUser, updatedUser);
        return "redirect:/users";
    }
}
