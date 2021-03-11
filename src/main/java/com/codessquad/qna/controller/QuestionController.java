package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    private QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("questions", questionService.findAll());
        return "/qna/list";
    }

    @PostMapping()
    public String question(Question question) {
        questionService.save(question);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        Question question = questionService.findQuestionById(id)
                .orElseThrow(() -> (new ResponseStatusException(HttpStatus.NOT_FOUND, "Question Not Found.")));
        model.addAttribute("question", question);
        return "/qna/show";
    }
}
