package com.codessquad.qna.controller;

import com.codessquad.qna.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("questions", questionService.findAll());
        return "index";
    }
}
