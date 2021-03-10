package com.codessquad.qna.controller;

import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.domain.user.UserRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/users")
@Controller
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/")
    public String CreateUser(User user) {
        logger.info(user.toString());
        userRepository.save(user);
        return "redirect:/users/";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/users/list";
    }

    @GetMapping("/{id}")
    public ModelAndView getProfile(@PathVariable Long id, Model model) {
        ModelAndView mav = new ModelAndView("/users/profile");
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. id = " + id));
        mav.addObject("user", user);
        return mav;
    }

    @GetMapping("/{id}/form")
    public ModelAndView getUpdateForm(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("/users/updateForm");
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. id = " + id));
        mav.addObject("user", user);
        return mav;
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable long id, User userWithUpdatedInfo, String oldPassword) {
        User targetUser = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. id = " + id));
        if (!targetUser.isCorrectPassword(oldPassword)) {
            logger.warn("비밀번호가 일치하지 않습니다.");
            return "redirect:/users/";
        }
        targetUser.update(userWithUpdatedInfo);
        userRepository.save(targetUser);
        logger.info(targetUser.toString());
        return "redirect:/users/";
    }
}
