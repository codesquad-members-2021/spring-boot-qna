package com.codessquad.qna.controller;

import com.codessquad.qna.HttpSessionUtils;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.dto.AnswerDto;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;

    public AnswerController(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @PostMapping("/questions/{id}/answers")
    public String createAnswer(@PathVariable long id, AnswerDto answerDto, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        Question question = questionService.findQuestionById(id);
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        answerService.create(answerDto, question, sessionedUser);
        return "redirect:/questions/" + id;
    }

    @DeleteMapping("/questions/{id}/answers/{answerId}")
    public String deleteAnswer(@PathVariable long id, @PathVariable long answerId, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        answerService.delete(answerId, sessionedUser);
        return "redirect:/questions/" + id;
    }
}
