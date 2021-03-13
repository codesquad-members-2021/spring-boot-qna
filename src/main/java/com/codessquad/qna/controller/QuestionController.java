package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/questions")
    public String question(Question question){

        questionService.registerQuestion(question);

        return "redirect:/";
    }

    @GetMapping("/")
    public String questionList(Model model) {

        model.addAttribute("questions", questionService.findQuestions());

        return "/index";
    }

    @GetMapping("/questions/{index}")
    public String showQuestionDetail(@PathVariable Long index, Model model) {

        model.addAttribute("question", questionService.findQuestion(index).get());

        return "/qna/show";
    }

}
