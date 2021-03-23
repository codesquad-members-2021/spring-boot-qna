package com.codessquad.qna.web.controllers;

import com.codessquad.qna.web.service.AnswerService;
import com.codessquad.qna.web.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;

    private AnswerController(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @PostMapping
    public String addAnswer(@PathVariable Long questionId, HttpSession session, String contents) {
        answerService.saveAnswerToQuestion(questionId, session, contents);
        return "redirect:/questions/" + questionId;
    }
}
