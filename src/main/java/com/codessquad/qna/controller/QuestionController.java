package com.codessquad.qna.controller;

import com.codessquad.qna.question.Question;
import com.codessquad.qna.question.QuestionRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    private final List<Question> questions = new ArrayList<>();

    @PostMapping("/questions")
    public String query(QuestionRequest questionRequest) {
        Question question = new Question(questionRequest);
        question.setId(questions.size() + 1);
        question.setTime(LocalDateTime.now());
        questions.add(question);
        return "redirect:/";
    }
}
