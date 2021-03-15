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

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @PostMapping
    public String create(User user) {
        if (user == null) {
            return "redirect:/";
        }
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping
    public String getList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String getOneUserProfile(@PathVariable long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(NoUserException::new));
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String editUserInfo(@PathVariable long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(NoUserException::new));
        return "user/updateForm";
    }

    @PostMapping("/{id}/form")
    public String update(@PathVariable long id, User updateUser, String newPassword) {
        User user = userRepository.findById(id).orElseThrow(NoUserException::new);
        if (user.checkPassword(updateUser.getPassword())) {
            user.update(updateUser, newPassword);
            userRepository.save(user);
        } else {
            logger.info("Error: 올바르지 않은 패스워드입니다.정보가 유지됩니다.");
        }
        return "redirect:/users";
    }

}
