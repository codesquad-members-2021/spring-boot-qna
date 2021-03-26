package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.util.HttpSessionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/api/questions/{id}/answers")
public class ApiAnswerController {
    private final AnswerService answerService;

    public ApiAnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public Answer createAnswer(@PathVariable Long id, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return null;
        }
        return answerService.create(id, contents, session);
    }

    @DeleteMapping("/{answerId}")
    public boolean deleteAnswer(@PathVariable Long id, @PathVariable Long answerId, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return false;
        }
        User loginUser = HttpSessionUtils.getSessionUser(session);
        return answerService.delete(answerId, loginUser);
    }

}
