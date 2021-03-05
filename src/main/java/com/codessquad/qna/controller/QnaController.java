package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QnaController {
    private List<Question> questions = new ArrayList<>();

    @PostMapping("/questions")
    public String createQuestion(Question question) {
        System.out.println(question);
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getQuestionList(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }
}
