package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.UserService;
import com.codessquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.util.HttpSessionUtils.USER_SESSION_KEY;

@Controller
@RequestMapping("/users")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String createUser(User newUser) {

        // newUser가 null이거나 속성이 빈값일 경우
        if (newUser.isEmpty()) {
            return "user/form";
        }

        User savedUser = userService.join(newUser);

        // 회원의 속성을 비교하여 정상적으로 회원가입 되었는 지 확인
        if (!savedUser.equals(newUser)) {
            return "user/form";
        }

        return "redirect:/users";
    }

    @GetMapping
    public String showUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());

        return "user/list";
    }

    @GetMapping("/{id}")
    public String showUserInDetail(@PathVariable long id, Model model) {
        model.addAttribute("user", userService.getOneById(id).orElse(null));

        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String moveToUpdateForm(@PathVariable long id, Model model, HttpSession session) {
        User user = userService.getOneById(id).orElse(null);

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);

        if (!sessionedUser.isEqualUserId(user.getUserId())) {
            throw new IllegalStateException("본인의 정보만 수정할 수 있습니다.");
        }

        model.addAttribute("id", user.getId());
        model.addAttribute("userId", user.getUserId());

        return "user/update";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable long id, User referenceUser, HttpSession session) {
        User presentUser = userService.getOneById(id).orElse(null);

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);

        if (presentUser == null) {
            logger.info("present empty");
            return "redirect:/users";
        }
        if (referenceUser.isEmpty()) {
            logger.info("reference empty");
            return "redirect:/users";
        }

        if (!presentUser.isEqualPassword(referenceUser.getPassword())) {
            logger.info("isEqualPassword");
            return "redirect:/users";
        }

        userService.updateInfo(presentUser, referenceUser);
        return "redirect:/users";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        // 아이디 존재여부 확인
        // TODO: userId에 해당하는 User가 없는 경우, 왜 null 처리가 되지 않나?
        User loginUser = userService.getOneByUserId(userId).orElse(null);
        if (loginUser == null) {
            return "redirect:/users/login";
        }

        // 비밀번호 일치여부 확인
        if (!loginUser.isEqualPassword(password)) {
            return "redirect:/users/login";
        }

        // 세션 처리
        session.setAttribute(USER_SESSION_KEY, loginUser);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);

        return "redirect:/";
    }
}
