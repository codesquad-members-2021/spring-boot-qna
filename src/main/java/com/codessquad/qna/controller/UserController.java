package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
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

        // 회원의 속성을 비교하여 정상적으로 회원가입 되었는 지 확인하는 로직으로 변경
        if (!savedUser.sameAs(newUser)) {
            return "user/form";
        }

        return "redirect:/users";
    }

    @GetMapping
    public String showUsers(Model model) {
        model.addAttribute("users", userService.showAll());

        return "/user/list";
    }

    @GetMapping("/{id}")
    public String showUserInDetail(@PathVariable long id, Model model) {
        model.addAttribute("user", userService.showOneById(id).orElse(null));

        return "/user/profile";
    }

    @GetMapping("/{id}/form")
    public String passUserId(@PathVariable long id, Model model) {
        User user = userService.showOneById(id).orElse(null);

        model.addAttribute("id", user.getId());
        model.addAttribute("userId", user.getUserId());

        return "/user/update";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable long id, User referenceUser) {
        User presentUser = userService.showOneById(id).orElse(null);

        if (presentUser == null || presentUser.isEmpty()) {
            logger.info("null or empty");
            return "redirect:/users";
        }

        if (!presentUser.isEqualPassword(referenceUser.getPassword())) {
            logger.info("isEqualPassword");
            return "redirect:/users";
        }

        logger.info("updateUserProperties");
        userService.updateInfo(presentUser, referenceUser);
        return "redirect:/users";
    }
}
