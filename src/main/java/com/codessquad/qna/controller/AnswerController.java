package com.codessquad.qna.controller;

import com.codessquad.qna.service.AnswerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}")
public class AnswerController {

    private static final Logger logger = LoggerFactory.getLogger(AnswerController.class);

    private final AnswerService answerService;

    public AnswerController(AnswerService answerservice) {
        this.answerService = answerservice;
    }

    @PostMapping("/answers")
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        answerService.createAnswer(questionId, contents, session);
        return ("redirect:/questions/" + questionId);
    }

    @DeleteMapping("/answers/{answerId}")
    public String remove(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session) {
        answerService.removeAnswer(answerId, session);
        return ("redirect:/questions/" + questionId);
    }

}
