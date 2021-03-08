package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.repository.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    QuestionRepository questionRepository = new QuestionRepository();

    @GetMapping("/")
    public String showQuestions(Model model) {

        model.addAttribute("questions", questionRepository.getAll());

        return "/index";
    }
}
