package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.user.User;

import com.codessquad.qna.web.domain.user.UserRepository;

import com.codessquad.qna.web.exception.CRUDAuthenticationException;
import com.codessquad.qna.web.exception.UserNotFoundException;
import com.codessquad.qna.web.utils.SessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("No user with userId " + userId));

        if (!user.isMatchingPassword(password)) {
            return "redirect:/users/login-form";
        }

        session.setAttribute("sessionedUser", user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionedUser");
        return "redirect:/";
    }

    @PostMapping("/create")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        User user = findUserById(id);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model, HttpSession session) {
        User user = findUserById(id);
        User loginUser = SessionUtils.getLoginUser(session);

        if (!loginUser.isMatchingWriter(user)) {
            throw new CRUDAuthenticationException("Users can only edit their own profile information");
        }

        model.addAttribute("user", user);

        return "user/updateForm";
    }

    @PutMapping("/{id}/update")
    public String updateProfile(@PathVariable long id, User updatedUser, String oldPassword) {
        User user = findUserById(id);

        if (user.isMatchingPassword(oldPassword)) {
            user.update(updatedUser);
            userRepository.save(user);
        }

        return "redirect:/users";
    }

    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No user with id number " + id));
    }
}
