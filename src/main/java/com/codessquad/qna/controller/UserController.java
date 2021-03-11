package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    private final String CONFIRM_INFO = "/confirm/{id}";
    private final String UPDATE_INFO = "/update/{id}";
    private final String LOGIN = "/login";
    private final String LOGOUT = "/logout";
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("")
    public String create(User user) {
        System.out.println("user Info: " + user);
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        System.out.println(model);
        return "user/list";
    }

    @GetMapping(LOGIN)
    public String loginFrom() {
        return "/user/login";
    }

    @PostMapping(LOGIN)
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return "redirect:/users/login";
        }
        if (!user.matchPassword(password)) {
            return "redirect:/users/login";
        }
        session.setAttribute("sessionUser", user);
        return "redirect:/";
    }

    @GetMapping(LOGOUT)
    public String logout(HttpSession session) {
        session.removeAttribute("sessionUser");
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElse(null);
        model.addAttribute("user", user);
        return "user/profile";
    }

    @GetMapping("/{id}/changeInfo")
    public String changeInfo(@PathVariable Long id, HttpSession httpSession) {
        if (!HttpSessionUtils.isLoginUser(httpSession)) {
            return "redirect:/users/login";
        }
        User tempUser = HttpSessionUtils.getSessionUser(httpSession);
        if (!tempUser.matchId(id)) {
            throw new IllegalStateException("자신의 정보만 수정 가능합니다.");
        }
        return "redirect:/users/confirm/{id}";
    }

    @GetMapping(CONFIRM_INFO)
    public ModelAndView confirmUserInfo(@PathVariable Long id) {
        return getUserRepository("/user/confirmUserInfo", id);
    }

    @PostMapping(CONFIRM_INFO)
    public String confirmUserInfo(@PathVariable Long id, String password) {
        User user = userRepository.findById(id).orElse(null);
        if (user.matchPassword(password)) {
            return "redirect:/users/update/{id}";
        }
        return "redirect:/users/confirm/{id}";
    }

    @GetMapping(UPDATE_INFO)
    public ModelAndView updateUserInfo(@PathVariable Long id) {

        return getUserRepository("user/updateForm", id);
    }

    @PutMapping(UPDATE_INFO)
    public String updateUserInfo(@PathVariable Long id, String password, String name, String email) {
        User user = userRepository.findById(id).orElse(null);
        user.updateUserInfo(password, name, email);
        userRepository.save(user);
        return "redirect:/users";
    }

    private ModelAndView getUserRepository(String viewName, Long id) {
        ModelAndView modelAndView = new ModelAndView(viewName);
        User user = userRepository.findById(id).orElse(null);
        modelAndView.addObject("userId", user.getUserId());
        return modelAndView;
    }

}
