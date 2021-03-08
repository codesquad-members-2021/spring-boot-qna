package com.codessquad.qna.Controller;

import com.codessquad.qna.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();

    @GetMapping("/form")
    public String form() {
        System.out.println(" form :: in");
        return "users/form";
    }

    @PostMapping("/user/create")
    public String create(User user) {
        users.add(user);

        return "redirect:/userslist";
    }



    @GetMapping("/userslist")
    public String list(Model model) {
        System.out.println("userslist");
        model.addAttribute("users",users);

        return "users/list";
    }
}
