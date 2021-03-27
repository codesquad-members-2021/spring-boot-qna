package com.codessquad.qna.controller;

import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.utils.HttpSessionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequestMapping("/api/questions/{questionId}/answers")
@RestController
public class ApiAnswerController {

    private final AnswerService answerService;

    public ApiAnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/")
    public Answer createAnswer(@PathVariable Long questionId, Answer answer, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return null;
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        return answerService.create(questionId, answer, sessionedUser);

    }

    @DeleteMapping("/{id}")
    public Answer delete(@PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return null;
        }
        Answer answer = answerService.findById(id);
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        answerService.deleteById(id, loginUser);
        return answer;
    }
}
