package com.codessquad.qna.controller;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotLoggedInException;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.util.HttpSessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        checkSessionUser(session);

        answerService.create(questionId, contents, session);
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{answerId}")
    public String deleteAnswer(@PathVariable long answerId, HttpSession session, Model model) {
        checkSessionUser(session);

        answerService.remove(getSessionUser(session), answerService.getOneById(answerId));
        return "redirect:/questions/{questionId}";
    }

    private void checkSessionUser(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new NotLoggedInException();
        }
    }

    private User getSessionUser(HttpSession session){
        return HttpSessionUtils.getUserFromSession(session);
    }
}
