package com.codesquad.qna.controller;

import com.codesquad.qna.domain.Answer;
import com.codesquad.qna.domain.User;
import com.codesquad.qna.service.AnswerService;
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

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public String create(@PathVariable Long id, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        Answer answer = new Answer(sessionedUser, contents);
        answerService.save(answer);

        return "redirect:/questions/{id}";
    }
}
