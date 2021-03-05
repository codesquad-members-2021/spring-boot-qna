package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("")
    public String createQuestion(Question question) {
        questionService.write(question);
        return "redirect:/";
    }

    @GetMapping("/{questionId}")
    public String renderQuestion(@PathVariable Long questionId, Model model) {
        Optional<Question> getQuestion = questionService.findById(questionId);

        model.addAttribute("question", getQuestion.orElseThrow(NullPointerException::new));
        return "qna/show";
    }

}
