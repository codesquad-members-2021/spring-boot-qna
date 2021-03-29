package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.service.AnswerService;
import com.codessquad.qna.web.utils.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}")
public class AnswersController {
    private final AnswerService answerService;

    public AnswersController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/answers")
    public String createAnswer(@PathVariable("questionId") long questionId, String answerContents,
                               HttpSession session) {
        User loginUser = SessionUtil.getLoginUser(session);
        answerService.createAnswer(loginUser, questionId, answerContents);
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/answers/{answerId}")
    public String deleteAnswer(@PathVariable("questionId") long questionId,
                               @PathVariable("answerId") long answerId, HttpSession session) {
        User loginUser = SessionUtil.getLoginUser(session);
        answerService.deleteAnswer(loginUser, answerId);
        return "redirect:/questions/" + questionId;
    }

}
