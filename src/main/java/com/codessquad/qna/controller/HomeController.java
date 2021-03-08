package com.codessquad.qna.controller;


import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private QuestionRepository questionRepository;

    public HomeController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping("/")
    public String welcome(Model model) {
        List<Question> getQuestions = questionRepository.findAll();
        model.addAttribute("Questions",getQuestions);
        return "index";
    }

}
