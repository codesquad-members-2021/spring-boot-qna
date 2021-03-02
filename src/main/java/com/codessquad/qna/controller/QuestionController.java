package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class QuestionController {

    Logger logger = LoggerFactory.getLogger(QuestionController.class);
    private List<Question> questions = new ArrayList<>();

    @GetMapping("qna/form")
    public String getQuestionForm() {
        return "/qna/form";
    }

    @PostMapping("qna/ask")
    public String askQuestion(Question question) {
        questions.add(question);
        logger.info(question.toString());

        return "redirect:/";
    }
}
