package com.codesquad.qna.controller;

import com.codesquad.qna.domain.Answer;
import com.codesquad.qna.domain.Question;
import com.codesquad.qna.domain.User;
import com.codesquad.qna.service.AnswerService;
import com.codesquad.qna.service.QuestionService;
import com.codesquad.qna.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{id}/answers")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    @Autowired
    public AnswerController(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @PostMapping
    public String create(@PathVariable Long id, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Question question = questionService.findQuestionById(id);
        Answer answer = new Answer(sessionedUser, question, contents);
        answerService.save(answer);

        return "redirect:/questions/{id}";
    }
}
