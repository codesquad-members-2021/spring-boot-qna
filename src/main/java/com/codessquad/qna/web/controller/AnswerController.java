package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.HttpSessionUtils;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exception.NotLoginException;
import com.codessquad.qna.web.service.AnswerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public String createAnswer(@PathVariable long questionId, String contents, HttpSession session) {
        User user = HttpSessionUtils.getSessionedUser(session).orElseThrow(NotLoginException::new);
        answerService.postAnswer(user, questionId, contents);
        return String.format("redirect:/questions/%d", questionId);
    }

    @DeleteMapping("/{id}")
    public String deleteAnswer(@PathVariable("questionId") long questionId, @PathVariable("id") long id, HttpSession session) {
        User loginUser = HttpSessionUtils.getSessionedUser(session).orElseThrow(NotLoginException::new);
        answerService.deleteAnswer(id, loginUser);
        return "redirect:/questions/{questionId}";
    }
}
