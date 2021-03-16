package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.exception.ForbiddenException;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.exception.NotLoggedInException;
import com.codessquad.qna.service.AnswerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {

    private final AnswerService answerService;
    private final Logger log = LoggerFactory.getLogger(ApiAnswerController.class);

    @Autowired
    public ApiAnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping()
    public Answer createAnswer(@PathVariable("questionId") Long questionId, Answer answer, HttpSession session){
        return answerService.create(questionId, answer, HttpSessionUtils.loginUser(session));
    }

    @DeleteMapping("/{answerId}")
    public String deleteAnswer(
            @PathVariable("questionId") Long questionId,
            @PathVariable("answerId") Long answerId, HttpSession session) {
        answerService.delete(questionId, answerId, HttpSessionUtils.loginUser(session));
        return "hello";
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotLoggedInException.class)
    public String handleNotLoggedIn() {
        return "redirect:/users/login";
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public String handleForbidden() {
        return "FORBIDDEN";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFound() {
        return "NOT_FOUND";
    }
}
