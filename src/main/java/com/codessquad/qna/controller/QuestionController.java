package com.codessquad.qna.controller;

import com.codessquad.qna.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    private static List<Question> questions = new ArrayList<>();

    public static List<Question> getQuestions() {
        return questions;
    }

    @GetMapping("/questions")
    public String goToCreateQuestion() {
        return "qnaForm";
    }

    @PostMapping("/questions")
    public String createQuestion(Question question) {
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
