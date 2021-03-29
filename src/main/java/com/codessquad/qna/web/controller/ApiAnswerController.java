package com.codessquad.qna.web.controller;

import com.codessquad.qna.web.HttpSessionUtils;
import com.codessquad.qna.web.Result;
import com.codessquad.qna.web.domain.Answer;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.exception.IllegalAccessException;
import com.codessquad.qna.web.exception.IllegalEntityIdException;
import com.codessquad.qna.web.exception.NotLoginException;
import com.codessquad.qna.web.service.AnswerService;
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
    public Answer createAnswer(@PathVariable long questionId, String contents, HttpSession session) {
        User user = HttpSessionUtils.getSessionedUser(session).orElseThrow(NotLoginException::new);
        return answerService.postAnswer(user, questionId, contents);
    }

    @DeleteMapping("/{id}")
    public Result deleteAnswer(@PathVariable("questionId") long questionId, @PathVariable("id") long id, HttpSession session) {
        User loginUser = HttpSessionUtils.getSessionedUser(session).orElseThrow(NotLoginException::new);
        answerService.deleteAnswer(id, loginUser);
        return Result.ok();
    }

    @ExceptionHandler(IllegalEntityIdException.class)
    public String handleIllegalEntityIdException(IllegalEntityIdException e) {
        return e.getMessage();
    }

    @ExceptionHandler(NotLoginException.class)
    public String handleNotLoginException(NotLoginException e) {
        return e.getMessage();
    }

    @ExceptionHandler(IllegalAccessException.class)
    public String handleIllegalAccessException(IllegalAccessException e) {
        return e.getMessage();
    }
}
