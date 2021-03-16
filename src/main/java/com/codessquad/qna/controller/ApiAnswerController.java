package com.codessquad.qna.controller;

import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.utils.HttpSessionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequestMapping("/api/questions/{questionId}/answers")
@RestController
public class ApiAnswerController {

    private final AnswerService answerService;

    ApiAnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/")
    public Answer createAnswer(@PathVariable Long questionId, Answer answer, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return null;
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        answer.setWriter(sessionedUser);
        return answerService.create(questionId, answer);

    }
}
