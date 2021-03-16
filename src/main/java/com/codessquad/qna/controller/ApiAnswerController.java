package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.service.AnswerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    private final AnswerService answerService;
    private final Logger log = LoggerFactory.getLogger(ApiAnswerController.class);

    @Autowired
    public ApiAnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping()
    public Answer createAnswer(@PathVariable("questionId") Long questionId, Answer answer, HttpSession session){
        return answerService.create(questionId, answer, HttpSessionUtils.loginUser(session));
    }
}
