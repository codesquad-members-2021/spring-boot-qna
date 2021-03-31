package com.codessquad.qna.web.controller.api;

import com.codessquad.qna.web.domain.Answer;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.service.AnswerService;
import com.codessquad.qna.web.utils.SessionUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    private final AnswerService answerService;

    public ApiAnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public Answer createAnswer(@PathVariable("questionId") long questionId, String answerContents,
                               HttpSession session) {
        User loginUser = SessionUtil.getLoginUser(session);
        return answerService.createAnswer(loginUser, questionId, answerContents);
    }

    @DeleteMapping("/{answerId}")
    public void deleteAnswer(@PathVariable("questionId") long questionId,
                             @PathVariable("answerId") long answerId, HttpSession session) {
        User loginUser = SessionUtil.getLoginUser(session);
        answerService.deleteAnswer(loginUser, answerId);
    }
}
