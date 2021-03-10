package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

        if (!isValidUser(newUser)) {
            return "user/form";
        }

        //TODO: join의 반환값으로 id를 받아서 정상 join 여부 확인
        userService.join(newUser);

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
        model.addAttribute("users", userService.findAll());

        return "/user/list";
    }

    @GetMapping("/{id}")
    public ModelAndView showUserInDetail(@PathVariable long id) {
        ModelAndView modelAndView = new ModelAndView("/user/profile");
        modelAndView.addObject("user", userService.findById(id));

        return modelAndView;
    }

    @GetMapping("/{id}/form")
    public String passUserId(@PathVariable long id, Model model) {
        User user = userService.findById(id);

        model.addAttribute("id", user.getId());
        model.addAttribute("userId", user.getUserId());

        return "/user/update";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable long id, User referenceUser) {
        User presentUser = userService.findById(id);

        if(!isValidUser(presentUser)){
            logger.info("isValidUser");
            return "redirect:/users";
        }

        if (!isEqualPassword(presentUser.getPassword(), referenceUser.getPassword())) {
            logger.info("isEqualPassword");
            return "redirect:/users";
        }
        logger.info("updateUserProperties");
        userService.updateInfo(presentUser, referenceUser);
        return "redirect:/users";
    }

    // TODO: 도메인 객체로 뺄 수 있다
    private boolean isEqualPassword(String real, String expected) {
        return real.equals(expected);
    }
}
