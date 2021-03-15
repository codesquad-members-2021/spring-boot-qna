package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public String createQuestion(Question question) {
        questionService.postQuestion(question);
        return "redirect:/questions";
    }

    @GetMapping
    public String getQuestions(Model model) {
        model.addAttribute("questions", questionService.findQuestions());
        return "/index";
    }

    @GetMapping("/{id}")
    public String getQuestion(@PathVariable long id, Model model) {
        try {
            model.addAttribute("question", questionService.findQuestion(id));
        } catch (IllegalStateException e) {
            return "redirect:/";
        }
        return "/qna/show";
    }
}
