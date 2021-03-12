package com.codessquad.qna.controller;

import com.codessquad.qna.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @GetMapping("/")
    public String goHome(Model model) {
        List<Question> questions = QuestionController.getQuestions();
        model.addAttribute("questions", questions);
        return "index";
    }
}
