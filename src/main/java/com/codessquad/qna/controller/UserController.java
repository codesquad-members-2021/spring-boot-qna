package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
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
@RequestMapping("/users")
public class UserController {
    @Autowired
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public String createUser(User newUser) {

        if (!isValidUser(newUser)) {
            return "users/form";
        }

        // TODO: 기존 회원과의 중복 여부 확인 로직 추가
        userRepository.save(newUser);

        return "redirect:/users";
    }

    private boolean isValidUser(User user) {
        if (user == null){
            return false;
        }
        if ("".equals(user.getUserId()) || user.getUserId() == null) {
            return false;
        }
        if ("".equals(user.getEmail()) || user.getEmail() == null) {
            return false;
        }
        if ("".equals(user.getPassword()) || user.getPassword() == null) {
            return false;
        }
        if ("".equals(user.getName()) || user.getName() == null) {
            return false;
        }

        return true;
    }

    @GetMapping
    public String showUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "/users/list";
    }

    @GetMapping("/{id}")
    public ModelAndView showUserInDetail(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("/users/profile");
        modelAndView.addObject("user", userRepository.findById(id));

        return modelAndView;
    }

    @GetMapping("/{userId}/form")
    public String passUserId(@PathVariable(name = "userId") String targetId, Model model) {
        model.addAttribute("userId", targetId);

        return "/users/update";
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable(name = "id") long id, User referenceUser) {
        User presentUser = userRepository.findById(id).get();

        if(!isValidUser(presentUser)){
            return "redirect:/users";
        }

        if (!isEqualPassword(presentUser.getPassword(), referenceUser.getPassword())) {
            return "redirect:/users";
        }

        updateUserProperties(presentUser, referenceUser);

        return "redirect:/users";
    }

    private boolean isEqualPassword(String real, String expected) {
        return real.equals(expected);
    }

    private void updateUserProperties(User presentUser, User referenceUser) {
        presentUser.setPassword(referenceUser.getPassword());
        presentUser.setName(referenceUser.getName());
        presentUser.setEmail(referenceUser.getEmail());
    }
}
