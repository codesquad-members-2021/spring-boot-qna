package com.codessquad.qna.Controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/form")
    public String signUpForm() {
        logger.info("signUpForm >> users/form.html: in");
        return "user/form";
    }

    @PostMapping("/create")
    public String userCreate(User user) {
        userRepository.save(user);
        return "redirect:/user/list";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("userlist",userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{userId}")
    public String showProfile(@PathVariable String userId, Model model) {
        User currentUser = getUserByUserId(userId);
        model.addAttribute("user",currentUser);
        logger.info("update User : " + currentUser.toString());
        return "/user/profile";
    }

    public User getUserByUserId(String userId) {
        for(User user: userRepository.findAll() ) {
            if(user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    @GetMapping("")
    public String userListShow() {
        return "redirect:/users/list";
    }

    @GetMapping("/{userId}/form")
    public String updateForm(@PathVariable(name="userId") String userId, Model model) {
        for(User user : userRepository.findAll() ) {
            if(user.getUserId().equals(userId)) {
                model.addAttribute("user",user);
                return "user/updateForm";
            }
        }
        return "index";
    }

    @PostMapping("/update")
    public String updateConfirm(User newUser) {
        return "redirect:/user";
    }

}
