package com.codessquad.qna.web.users;

import com.codessquad.qna.web.exceptions.auth.UnauthorizedAccessException;
import com.codessquad.qna.web.exceptions.users.UserNotFoundException;
import com.codessquad.qna.web.utils.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public String createUser(User createdUser) {
        userRepository.save(createdUser);
        LOGGER.info("user created : {}", createdUser.getUserId());
        return "redirect:/users";
    }

    @GetMapping
    public String getUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String getOneUser(@PathVariable("userId") long id, Model model) {
        User foundUser = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        model.addAttribute("foundUser", foundUser);
        return "user/profile";
    }

    @GetMapping("/modify-form")
    public String getModifyUserPage() {
        return "user/modify-form";
    }

    @PutMapping
    public String modifyUser(String prevPassword, String newPassword,
                             String name, String email, HttpSession session) {
        User loginUser = SessionUtil.getLoginUser(session);
        verifyAuthorizedAccess(loginUser, prevPassword);
        loginUser.update(newPassword, name, email);
        userRepository.save(loginUser);
        return "redirect:/users/" + loginUser.getId();
    }

    @PostMapping("/login")
    public String processLogin(String userId, String password, HttpSession session) {
        User foundUser = userRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);
        verifyAuthorizedAccess(foundUser, password);
        SessionUtil.setLoginUser(session, foundUser);
        LOGGER.info("user login : {}", foundUser.getUserId());
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String processLogout(HttpSession session) {
        User sessionUser = SessionUtil.getLoginUser(session);
        LOGGER.info("user logout : {}", sessionUser.getUserId());
        SessionUtil.removeLoginUser(session);
        return "redirect:/";
    }

    private void verifyAuthorizedAccess(User user, String password) {
        if (!user.isMatchingPassword(password)) {
            throw new UnauthorizedAccessException();
        }
    }
}
