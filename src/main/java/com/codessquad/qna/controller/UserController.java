package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(UserController.class);

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

    @GetMapping("/{id}/form")
    public String passUserId(@PathVariable long id, Model model) {
        User user = userRepository.findById(id).get();

        model.addAttribute("id", user.getId());
        model.addAttribute("userId", user.getUserId());

        return "/users/update";
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable long id, User referenceUser) {
        User presentUser = userRepository.findById(id).get();

        if(!isValidUser(presentUser)){
            logger.info("isValidUser");
            return "redirect:/users";
        }

        if (!isEqualPassword(presentUser.getPassword(), referenceUser.getPassword())) {
            logger.info("isEqualPassword");
            return "redirect:/users";
        }
        logger.info("updateUserProperties");
        updateUserProperties(presentUser, referenceUser);
        return "redirect:/users";
    }

    private boolean isEqualPassword(String real, String expected) {
        return real.equals(expected);
    }

    private void updateUserProperties(User presentUser, User referenceUser) {

        referenceUser.setUserId(presentUser.getUserId());

        // TODO: 반환값을 활용하여 예외 처리
        userRepository.delete(presentUser);
        userRepository.save(referenceUser);
    }
}
