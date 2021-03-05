package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final List<Question> questions = new ArrayList<>();

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("questions", questions);
        return "qna/list";
    }

    @PostMapping("")
    public String create(Question question) {
        LocalDateTime now = LocalDateTime.now();
        String formatDateTime = now.format(formatter);

        question.setId(questions.size() + 1);
        question.setCreatedDate(formatDateTime);

        questions.add(question);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String list(@PathVariable("id") long id, Model model) {
        model.addAttribute("question", questions.get((int) id - 1));
        return "/qna/show";
    }
}
