package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Result;
import com.codessquad.qna.domain.answer.Answer;
import com.codessquad.qna.domain.user.User;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.utils.HttpSessionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequestMapping("/api/questions/{questionId}/answers")
@RestController
public class ApiAnswerController {

    private final AnswerService answerService;

    ApiAnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/")
    public Answer createAnswer(@PathVariable Long questionId, Answer answer, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return null;
        }
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        answer.setWriter(sessionedUser);
        return answerService.create(questionId, answer);

    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return Result.fail("로그인해야 합니다.");
        }

        Answer answer = answerService.findById(id);
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!answer.isWrittenBy(loginUser)) {
            return Result.fail("자신의 글만 삭제할 수 있습니다.");
        }

        answerService.deleteById(id);
        return Result.ok();
    }
}
