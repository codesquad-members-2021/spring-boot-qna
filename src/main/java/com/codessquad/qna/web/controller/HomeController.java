package com.codessquad.qna.web.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {

    @GetMapping("/")
    public String getHome(){
        return "index";
    }
}
