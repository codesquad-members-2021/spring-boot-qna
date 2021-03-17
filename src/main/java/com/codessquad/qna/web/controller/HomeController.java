package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.question.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final QuestionRepository questionRepository;

    public HomeController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping("/")
    public String getHome(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

}
