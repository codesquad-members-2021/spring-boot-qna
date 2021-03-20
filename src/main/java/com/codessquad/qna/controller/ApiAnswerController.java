package com.codessquad.qna.controller;

import com.codessquad.qna.model.Answer;
import com.codessquad.qna.service.AnswerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.controller.HttpSessionUtils.getUserFromSession;

@RestController
public class ApiAnswerController {

    private final Logger logger = LoggerFactory.getLogger(AnswerController.class);
    private final AnswerService answerService;

    public ApiAnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("api/answer/{questionId}")
    public Answer createAnswer(@PathVariable("questionId") Long questionId, Answer answer, HttpSession session) {
        logger.info("댓글 등록 요청");
        return this.answerService.save(questionId, answer, getUserFromSession(session));
    }

}
