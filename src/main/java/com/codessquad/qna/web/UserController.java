package com.codessquad.qna.web;

import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.domain.user.UserRepository;
import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.codessquad.qna.domain.user.HttpSessionUtils.getUserFromSession;

@Controller
@RequestMapping("/users")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/form")
    public String getUserFormPage() {
        return "user/form";
    }

    @PostMapping("/")
    public String createUser(User user) {
        userRepository.save(user);
        logger.debug("user : {}", user);
        return "redirect:/users/";
    }

    @GetMapping("/")
    public String getUserList(Model model) {
        model.addAttribute("userList", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String getUserProfile(@PathVariable Long id, HttpSession session) {
        User user = getUserFromSession(session);

        if (!user.matchId(id)) {
            throw new IllegalUserAccessException();
        }

        logger.debug("user : {}", user);

        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String getUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        User loginedUser = getUserFromSession(session);

        if (!loginedUser.matchId(id)) {
            throw new IllegalUserAccessException();
        }

        Optional<User> user = Optional.ofNullable(userRepository.findById(id))
                .orElseThrow(UserNotFoundException::new);
        model.addAttribute("user", user.get());
        logger.debug("user : {}", user.get());

        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String prevPassword,
                         User updateUser, HttpSession session) {
        User loginedUser = getUserFromSession(session);

        if (!loginedUser.matchId(id)) {
            throw new IllegalUserAccessException();
        }

        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        if (!user.matchPassword(prevPassword)) {
            return "redirect:/users/";
        }

        userRepository.save(user.update(updateUser));
        logger.debug("user : {}", updateUser);

        return "redirect:/users/";
    }

    @GetMapping("/loginForm")
    public String getLoginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);

        if (!user.matchPassword(password)) {
            logger.error("로그인에 실패하셨습니다.");
            return "user/login_failed";
        }

        logger.debug("login : {}", user);
        session.setAttribute("sessionedUser", user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logOut(HttpSession session) {
        session.removeAttribute("sessionedUser");
        return "redirect:/";
    }
}
