package com.codessquad.qna.controller.api;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotLoggedInException;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.util.HttpSessionUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("api/questions/{questionId}/answers")
public class ApiAnswerController {
    private final AnswerService answerService;

    public ApiAnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        checkSessionUser(session);

        return answerService.create(questionId, contents, session);
    }

    @DeleteMapping("/{answerId}")
    public String deleteAnswer(@PathVariable long answerId, HttpSession session, Model model) {
        checkSessionUser(session);

        Answer answer = answerService.getOneById(answerId);

        checkAccessibleSessionUser(session, answer);

        answerService.remove(answer);
        return "redirect:/questions/{questionId}";
    }

    private void checkSessionUser(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new NotLoggedInException();
        }
    }

    private void checkAccessibleSessionUser(HttpSession session, Answer answer) {
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        if (!answer.isEqualWriter(sessionUser)) {
            throw new NotLoggedInException("자신의 글만 수정 및 삭제가 가능합니다.");
        }
    }

}
