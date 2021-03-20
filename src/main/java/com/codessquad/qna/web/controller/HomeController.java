package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String goHome() {
        return "forward:/questions";
    }
}
