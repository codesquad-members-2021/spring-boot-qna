package com.codessquad.qna.controller;

import com.codessquad.qna.model.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
public class QuestionController {

    private List<Question> questions = new ArrayList<>();

    @PostMapping("/questions")
    public String create(Question question) {
        question.setId(questions.size() + 1);
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @GetMapping("/questions/{id}")
    public String show(@PathVariable("id") int index, Model model) {
        Question question = questions.get(index - 1);
        model.addAttribute("question", question);
        return "qna/show";
    }
}
