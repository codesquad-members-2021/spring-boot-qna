package com.codessquad.qna.controller;

import com.codessquad.qna.repository.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    private List<Question> questions = new ArrayList<>();

    @GetMapping("/questions/new")
    public String newQuestion() {
        return "qnaForm";
    }

    @GetMapping("/questions")
    public String goToCreateQuestion(Model model) {
        model.addAttribute("questions", questions);
        return "index";
    }

    @PostMapping("/questions")
    public String createQuestion(Question question) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formatTime = format.format(System.currentTimeMillis());
        question.setDateTime(formatTime);
        question.setIndex(questions.size() + 1);
        questions.add(question);
        return "redirect:/";
    }

    @GetMapping("/questions/{index}")
    public String showQuestion(@PathVariable("index") int index, Model model) {
        Question question = questions.get(index - 1);
        model.addAttribute("question", question);
        return "show";
    }
}
