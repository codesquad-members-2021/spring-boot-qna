package com.codessquad.qna.controller;

import com.codessquad.qna.entity.Question;
import com.codessquad.qna.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("questions", questionService.getQuestions());
        return "index";
    }

    @PostMapping
    public String create(Question question) {
        question.setWriteDateTime(LocalDateTime.now());
        questionService.addQuestion(question);

        return "redirect:/";
    }

    @GetMapping("/{index}")
    public String detail(@PathVariable int index, Model model) {
        Optional<Question> question = questionService.getQuestion(index);
        if (question.isPresent()) {
            model.addAttribute("question", question.get());
            return "qna/show";
        }

        return "redirect:/";
    }
}
