package com.codessquad.qna.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private List<UserInfo> userInfos = new ArrayList<>();

    @PostMapping("/user/create")
    public String create(UserInfo userinfo){
        userInfos.add(userinfo);
        System.out.println("user : " + userinfo);
        return "index";
    }

    @GetMapping("/user/list")
    public String list(){
        return "list";
    }
}
