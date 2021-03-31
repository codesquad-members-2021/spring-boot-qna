package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import com.codessquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostMapping
    public String createUserAccount(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/profile/{id}")
    public String showProfile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElse(null));
        return "/user/profile";
    }


    @GetMapping("/{id}/updateForm")
    public String showUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (! HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/user/login";
        }
        if (! sessionedUser.matchId(id)) {
            throw new IllegalArgumentException("자신의 정보만 수정할 수 있습니다.");
        }
        model.addAttribute("user", sessionedUser);
        return "/user/update";
    }

    @PutMapping("/{id}/update")
    public String updateUserInfo(@PathVariable Long id, User updateUser, HttpSession session) {
        User sessionedUser = (User) session.getAttribute(HttpSessionUtils.USER_SESSION_KEY);
        if (! HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        if (!sessionedUser.matchId(id)) {
            throw new IllegalArgumentException("자신의 정보만 수정할 수 있습니다.");
        }
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 사용자가 존재하지 않습니다."));

        if(! sessionedUser.matchPassword(updateUser.getPassword())) {
            throw new IllegalStateException("비밀번호가 틀렸습니다.");
        }
        user.update(updateUser);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/login";
    }

    @GetMapping("/loginAgain")
    public String loginAgain() {
        return "/user/login_failed";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return "redirect:/users/loginAgain";
        }
        if (! user.matchPassword(password)) {
            return "redirect:/users/loginAgain";
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
