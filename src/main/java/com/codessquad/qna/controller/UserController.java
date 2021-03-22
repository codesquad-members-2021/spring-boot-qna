package com.codessquad.qna.controller;

import com.codessquad.qna.repository.User;
import com.codessquad.qna.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String showUserList(Model model) {
        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "userList";
    }

    @GetMapping("/new")
    public String toSignupPage() {
        return "userSignup";
    }

    @PostMapping
    public String signup(User user) {
        userRepository.save(user);
        return "redirect:/user";
    }

    @GetMapping("/{id}")
    public ModelAndView showProfile(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("userProfile");
        modelAndView.addObject("user", userRepository.findById(id).get());
        return modelAndView;
    }

    @GetMapping("/{userId}/password-check")
    public String toCheckPasswordPage(@PathVariable("userId") String userId, Model model) {
        model.addAttribute("userId", userId);
        return "passwordCheckForm";
    }

    @PostMapping("/{userId}/password-check")
    public String checkPassword(User targetUser, Model model) {
        User user = userRepository.findByUserId(targetUser.getUserId());
        String passwordBefore = user.getPassword();
        if (passwordBefore.equals(targetUser.getPassword())) {
            model.addAttribute("user", user);
            return "userUpdateForm";
        }
        return "redirect:/";
    }

//    @PostMapping("/{userId}/edit")
//    public String updateUser(@PathVariable("userId") String userId, User targetUser) {
//        Iterable<User> users = userRepository.findAll();
//        for (User user : users) {
//            if (user.isSameId(userId)) {
//                user.setPassword(targetUser.getPassword());
//                user.setName(targetUser.getName());
//                user.setEmail(targetUser.getEmail());
//                break;
//            }
//        }
//        return "redirect:/user";
//    }
}

