package com.codessquad.qna;

import com.codessquad.qna.questions.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    private final QuestionService questionService;

    @Autowired
    public MainController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    private String mainPage(Model model) {
        model.addAttribute("questions", questionService.getQuestions());
        return "index";
    }
}
