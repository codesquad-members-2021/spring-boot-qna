package com.codessquad.qna.question.ui;

import com.codessquad.qna.question.application.QuestionService;
import com.codessquad.qna.question.domain.Question;
import com.codessquad.qna.question.dto.QuestionRequest;
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

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping(value = "/create")
    public String createQuestion(Question question) {
        questionService.saveQuestion(QuestionRequest.of(question));
        return "redirect:/questions";
    }

    @GetMapping
    public String getQuestions(Model model) {
        model.addAttribute("questions", questionService.getQuestions());
        return "question/list";
    }

    @GetMapping(value = "/{id}")
    public String getQuestion(@PathVariable("id") Long id, Model model) {
        model.addAttribute("question", questionService.getQuestion(id));
        return "question/show";
    }
}
