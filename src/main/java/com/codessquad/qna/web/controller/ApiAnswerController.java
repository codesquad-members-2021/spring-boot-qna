package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.answer.Answer;
import com.codessquad.qna.web.domain.user.User;
import com.codessquad.qna.web.service.AnswerService;
import com.codessquad.qna.web.utils.SessionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    private AnswerService answerService;

    public ApiAnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public Answer create(@PathVariable long questionId, String contents, HttpSession session) {
        User loginUser = SessionUtils.getLoginUser(session);
        return answerService.create(questionId, contents, loginUser);
    }
}
