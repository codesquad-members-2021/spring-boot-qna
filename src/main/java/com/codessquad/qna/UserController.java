package com.codessquad.qna;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class UserController {

    private List<User> userList = new ArrayList<>();

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("questionList", QuestionController.questionList());
        return "index";
    }

    @GetMapping("/qna/form")
    public String qnaForm() {
        return "/qna/form";
    }

    @GetMapping("/user/form")
    public String userForm() {
        return "/user/form";
    }

    @GetMapping("/user/login")
    public String login() {
        return "/user/login";
    }

    @PostMapping("/user/create")
    public String create(User user) {
        System.out.println("user: " + user.toString());
        userList.add(user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/list")
    public String list(Model model) {
        model.addAttribute("userList", userList);
        return "/user/list";
    }

    @GetMapping("/userList/{userId}")
    public String profile(@PathVariable String userId, Model model) {
        for (User user : userList) {
            if(user.getUserId().equals(userId)) {
                model.addAttribute("user", user);
            }
        }
        return "/qna/profile";
    }

    @GetMapping("/userList/{userId}/update")
    public String updateUser(@PathVariable String userId, Model model) {
        for (User user : userList) {
            if(user.getUserId().equals(userId)) {
                model.addAttribute("user", user);
            }
        }
        return "/user/updateForm";
    }
}
