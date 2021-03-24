package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.domain.user.User;
import com.codessquad.qna.web.dto.user.CreateUserRequest;
import com.codessquad.qna.web.service.AnswerService;
import com.codessquad.qna.web.utils.SessionUtils;
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
    public String create(@PathVariable long questionId, String contents, HttpSession session) {
        User loginUser = SessionUtils.getLoginUser(session);
        answerService.create(questionId, contents, loginUser);
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{answerId}")
    public String delete(@PathVariable long questionId, @PathVariable long answerId, HttpSession session) {
        User loginUser = SessionUtils.getLoginUser(session);
        answerService.delete(questionId, answerId, loginUser);
        return "redirect:/questions/" + questionId;
    }
}
