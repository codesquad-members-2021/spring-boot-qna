package com.codessquad.qna.controller;

import com.codessquad.qna.Question;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    private static List<Question> questions = new ArrayList<>();

    @GetMapping("/questions")
    public String goToCreateQuestion() {
        return "qnaForm";
    }

    @PostMapping("/questions")
    public String createQuestion(Question question) {
        System.out.println(question);
        questions.add(question);
        return "redirect:/";
    }

    public static List<Question> getQuestions() {
        return questions;
    }
}
