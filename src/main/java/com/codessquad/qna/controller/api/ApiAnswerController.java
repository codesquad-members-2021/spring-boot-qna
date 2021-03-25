package com.codessquad.qna.controller.api;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.service.AnswerService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.util.HttpSessionUtils.checkSessionUser;
import static com.codessquad.qna.util.HttpSessionUtils.getSessionUser;

@RestController
@RequestMapping("api/questions/{questionId}/answers")
public class ApiAnswerController {
    private final AnswerService answerService;

    public ApiAnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/{answerId}")
    public Answer showInDetail(@PathVariable long answerId) {
        return answerService.getOneById(answerId);
    }

    @PostMapping
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        checkSessionUser(session);

        return answerService.create(questionId, contents, session);
    }

    @PutMapping("/{answerId}")
    public Answer update(@PathVariable long answerId, Answer newAnswerInfo, HttpSession session) {
        checkSessionUser(session);

        return answerService.update(answerService.getOneById(answerId), newAnswerInfo, getSessionUser(session));
    }

    @DeleteMapping("/{answerId}")
    public Answer delete(@PathVariable long answerId, HttpSession session) {
        checkSessionUser(session);

        return answerService.remove(getSessionUser(session), answerService.getOneById(answerId));
    }
}
