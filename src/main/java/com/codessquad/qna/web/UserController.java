package com.codessquad.qna.web;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import com.codessquad.qna.exception.NoUserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public String create(User user) {
        if (checkEmpty(user)) {
            return "user/form";
        }
        userRepository.save(user);
        return "redirect:/users";

    }

    private boolean checkEmpty(User user) {
        return user.getUserId().equals("")
                || user.getPassword().equals("")
                || user.getEmail().equals("")
                || user.getName().equals("");
    }

    @GetMapping
    public String getList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String getOneUserProfile(@PathVariable long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(NoUserException::new);

        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String editUserInfo(@PathVariable long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);


        if (sessionUser.checkId(id)) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있습니다.");
        }

        model.addAttribute("user", sessionUser);

        return "user/updateForm";
    }

    @PostMapping("/{id}/form")
    public String update(@PathVariable long id, User updateUser, String newPassword) {
        User user = userRepository.findById(id).orElseThrow(NoUserException::new);
        if (!user.checkPassword(updateUser.getPassword())) {
            logger.info("Error: 올바르지 않은 패스워드입니다.정보가 유지됩니다.");
        }
        user.update(updateUser, newPassword);

        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);
        if (HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/login";
        }
        if (!user.checkPassword(password)) {
            return "redirect:/users/login";
        }
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

}
