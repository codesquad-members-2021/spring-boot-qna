package com.codessquad.qna.Controller;

import com.codessquad.qna.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<User> users = new ArrayList<>();

    @PostMapping("/create")
    public String create() {

        System.out.println("create");
        return "users/form";
    }



    @GetMapping("/list")
    public String list() {
        System.out.println("list");
        return "users/list";
    }
}
