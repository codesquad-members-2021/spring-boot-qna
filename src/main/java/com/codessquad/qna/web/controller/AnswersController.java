package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.Question;
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
@RequestMapping("/questions")
public class AnswersController {
    private final AnswerService answerService;

    public AnswersController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/{questionId}/answers")
    public String createAnswer(@PathVariable("questionId") long questionId, String answerContents,
                               HttpSession session) {
        User sessionUser = SessionUtil.getLoginUser(session);
        answerService.createAnswer(sessionUser, questionId, answerContents);
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{questionId}/answers/{answerId}")
    public String deleteAnswer(@PathVariable("answerId") long answerId, HttpSession session) {
        User sessionUser = SessionUtil.getLoginUser(session);
        Question targetQuestion = answerService.deleteAnswer(sessionUser, answerId);
        return "redirect:/questions/" + targetQuestion.getId();
    }

}
