package com.codessquad.qna.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    private String questionsList(Model model) {
        model.addAttribute("questions", QuestionController.getQuestionList());
        return "index";
    }
}
