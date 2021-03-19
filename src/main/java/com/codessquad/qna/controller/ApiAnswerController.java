package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.AnswerService;
import org.springframework.stereotype.Controller;
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
    public String deleteAnswer(@PathVariable Long id, @PathVariable("answerId") Long answerId, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }
        Answer answer = answerService.findById(answerId);
        User loginUser = HttpSessionUtils.getSessionUser(session);
        if (answer.isMatch(loginUser)) {
            answerService.delete(answer);
        }
        return "redirect:/questions/" + id;
    }

}
