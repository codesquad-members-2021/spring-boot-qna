package com.codessquad.qna.Controller;

import com.codessquad.qna.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);
    final private List<User> userList = new ArrayList<>();

    @GetMapping("users/form.html")
    public String signUpForm() {
        logger.info("signUpForm >> users/form.html: in");
        return "users/form";
    }

    @PostMapping("/users/create")
    public String userCreate(User user) {
        userList.add(user);
        return "redirect:/users/list";
    }

    @GetMapping("/users/list")
    public String list(Model model) {
        logger.info("userslist");
        model.addAttribute("userlist",userList);
        return "users/list";
    }

    @GetMapping("/users/{userId}")
    public String showProfile(@PathVariable String userId, Model model) {
        User currentUser = getUserByUserId(userId);
        model.addAttribute("user",currentUser);
        logger.info("update User : " + currentUser.toString());
        return "/users/profile";
    }

    public User getUserByUserId(String userId) {
        for(User user: userList) {
            if(user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    @GetMapping("/users")
    public String userListShow() {
        return "redirect:/users/list";
    }

    @GetMapping("/users/{userId}/form")
    public String updateForm(@PathVariable(name="userId") String userId, Model model) {
        System.out.println("updateForm@@@");
        User currentUser;
        for(User user : userList) {
            if(user.getUserId().equals(userId)) {
                model.addAttribute("user",user);
                break;
            }
        }
        return "users/updateForm";
    }

    @PostMapping("/users/update")
    public String updateConfirm(User newUser) {
        return "redirect:/users";
    }


}
