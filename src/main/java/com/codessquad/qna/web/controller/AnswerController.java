package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.HttpSessionUtils;
import com.codessquad.qna.web.domain.Answer;
import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.exception.IllegalAccessException;
import com.codessquad.qna.web.exception.NotLoginException;
import com.codessquad.qna.web.service.AnswerService;
import com.codessquad.qna.web.service.QuestionService;
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
    private final QuestionService questionService;

    public AnswerController(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @PostMapping
    public String createAnswer(@PathVariable long questionId, String contents, HttpSession session) {
        checkLogin(session);
        answerService.postAnswer(HttpSessionUtils.getSessionedUser(session), questionService.findQuestion(questionId), contents);
        return String.format("redirect:/questions/%d", questionId);
    };

    @DeleteMapping("/{id}")
    public String deleteAnswer(@PathVariable("questionId") long questionId, @PathVariable("id") long id, HttpSession session) {
        checkLogin(session);
        answerService.deleteAnswer(validateAndGetAnswer(id, session));
        return "redirect:/questions/{questionId}";
    }

    private void checkLogin(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new NotLoginException();
        }
    }

    private Answer validateAndGetAnswer(long id, HttpSession session) {
        Answer answer = answerService.findAnswer(id);
        if(!answer.isSameWriter(HttpSessionUtils.getSessionedUser(session))) {
            throw new IllegalAccessException();
        }
        return answer;
    }
}
