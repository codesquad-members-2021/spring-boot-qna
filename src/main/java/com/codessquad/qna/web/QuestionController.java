package com.codessquad.qna.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {
    private List<Question> questions = new ArrayList<>();

    @PostMapping("/questions")
    public String createQuestion(Question question, Model model){
        questions.add(question);
        model.addAttribute("qeustions", question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String getQuestion(Model model){
        model.addAttribute("qeustions", questions);
        return "/index";
    }
}
