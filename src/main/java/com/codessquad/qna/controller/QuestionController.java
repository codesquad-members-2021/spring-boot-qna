package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private final List<Question> questions = new ArrayList<>();

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("questions", questions);
        return "qna/list";
    }

    @PostMapping("")
    public String create(Question question) {
        question.setId(questions.size() + 1);
        questions.add(question);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        model.addAttribute("question", questions.get((int) id - 1));
        return "/qna/show";
    }
}
