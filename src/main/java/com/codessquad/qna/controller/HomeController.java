package com.codessquad.qna.controller;

import com.codessquad.qna.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final QuestionService questionService;

    public HomeController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("questions", questionService.getQuestionList());
        return "index";
    }
}
