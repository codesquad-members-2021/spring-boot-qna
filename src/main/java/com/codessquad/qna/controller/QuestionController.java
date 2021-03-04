package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private final List<Question> questions = new ArrayList<>();

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("questions", questions);
        return "qna/list";
    }

    @PostMapping("")
    public String create(Question question) {
        Date date = new Date(System.currentTimeMillis());

        question.setId(questions.size() + 1);
        question.setCreateDate(date);

        questions.add(question);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String list(@PathVariable("id") long id, Model model) {
        model.addAttribute("question", questions.get((int) id - 1));
        return "/qna/show";
    }
}
