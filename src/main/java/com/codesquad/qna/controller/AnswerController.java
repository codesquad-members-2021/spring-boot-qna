package com.codesquad.qna.controller;

import com.codesquad.qna.domain.Answer;
import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.User;
import com.codesquad.qna.exception.IllegalUserAccessException;
import com.codesquad.qna.service.AnswerService;
import com.codesquad.qna.service.QuestionService;
import com.codesquad.qna.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question question = answerService.findQuestionById(questionId);
        Answer answer = new Answer(sessionedUser, question, contents);

        answerService.save(answer);

        return "redirect:/questions/{questionId}";
    }

    @DeleteMapping("{answerId}")
    public String delete(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Answer answer = answerService.findAnswerById(answerId);

        if (!sessionedUser.isMatchedUserId(answer.getUserId())) {
            throw new IllegalUserAccessException();
        }

        answerService.delete(answer);

        return "redirect:/questions/{questionId}";
    }
}
