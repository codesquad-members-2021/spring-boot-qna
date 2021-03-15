package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.service.AnswerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/questions/{id}/answers")
public class AnswerController {
    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public String createAnswer(@PathVariable Long id, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }
        answerService.create(id, contents, session);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/{answerId}")
    public String deleteAnswer(@PathVariable Long id, @PathVariable("answerId") Long answerId, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/user/login";
        }
        Answer answer = answerService.findById(answerId);
        User loginUser = HttpSessionUtils.getSessionUser(session);
        if (answer.matchUser(loginUser)) {
            answerService.delete(answer);
            return "redirect:/questions/" + id;
        }
        return "redirect:/questions/" + id;
    }

}
