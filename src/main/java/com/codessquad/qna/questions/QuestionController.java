package com.codessquad.qna.questions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    private String createQuestion(Question question) {
        question.setDateTime(LocalDateTime.now());
        questionService.addQuestion(question);

        return "redirect:/";
    }

    @GetMapping("/{index}")
    private String questionDetail(@PathVariable int index, Model model) {
        Optional<Question> question = questionService.getQuestion(index);
        if (question.isPresent()) {
            model.addAttribute("question", question.get());
            return "qna/show";
        }

        return "redirect:/";
    }
}
