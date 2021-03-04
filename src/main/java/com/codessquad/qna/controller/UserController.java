package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RequestMapping("/users")
@Controller
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        Optional<User> userTemp = userService.findByUserId(userId);
        if (!userTemp.isPresent()) {
            logger.info("로그인에 실패했습니다.");
            return "redirect:/users/loginForm";
        }
        User user = userTemp.get();
        if (!password.equals(user.getPassword())) {
            return "redirect:/users/loginForm";
        }

        session.setAttribute("sessionUser", user);
        logger.info("로그인에 성공했습니다.");
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionUser");
        return "redirect:/";
    }

    @PostMapping("")
    public String create(UserForm form) {
        User user = new User();
        user.setUserId(form.getUserId());
        user.setPassword(form.getPassword());
        user.setName(form.getName());
        user.setEmail(form.getEmail());
        userService.join(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        List<User> users = userService.findUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        Optional<User> user = userService.findUser(id);
        if (!user.isPresent()) {
            return "redirect:/users";
        }
        model.addAttribute(user.get());
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser==null) {
            return "redirect:/users";
        }
        if (!id.equals(sessionUser.getId())) {
            throw new IllegalStateException("잘못된 접근입니다.");
        }
        Optional<User> tempUser = userService.findUser(id);
        if (!tempUser.isPresent()) {
            return "redirect:/users";
        }
        User user = tempUser.get();
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User newUser) {
        if (!userService.findUser(id).isPresent()) {
            return "redirect:/users";
        }
        User user = userService.findUser(id).get();
        user.update(newUser);
        userService.join(user);
        return "redirect:/users";
    }

}
