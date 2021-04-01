package com.codessquad.qna.controller.api;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.validationGroup.Submit;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AnswerService answerService;

    @Autowired
    public ApiAnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Answer create(@PathVariable long questionId, @Validated(Submit.class) Answer answer, HttpSession session) {
        logger.debug("{}번 질문에 답변 생성 요청", questionId);
        User user = HttpSessionUtils.getUser(session);
        return answerService.addAnswer(questionId, user, answer.getContents());
    }

    @DeleteMapping("/{answerId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long questionId, @PathVariable long answerId, HttpSession session) {
        logger.debug("{}번 질문의 {}번 답변 삭제 요청", questionId, answerId);
        User user = HttpSessionUtils.getUser(session);
        answerService.deleteAnswer(answerId, user);
    }
}
