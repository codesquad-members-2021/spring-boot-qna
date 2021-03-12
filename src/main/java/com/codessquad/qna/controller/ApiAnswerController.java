package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.controller.HttpSessionUtils.getSessionUser;
import static com.codessquad.qna.controller.HttpSessionUtils.isLoginUser;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    private static final Logger logger = LoggerFactory.getLogger(ApiAnswerController.class);
    private final QuestionService questionService;
    private final AnswerService answerService;

    @Autowired
    public ApiAnswerController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @PostMapping
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!isLoginUser(session)) {
            throw new IllegalUserAccessException("로그인이 필요합니다.");
        }
        User user = getSessionUser(session);
        Question question = questionService.findQuestion(questionId);
        Answer answer = new Answer(user, question, contents);
        logger.info("{}님께서 답변 작성에 성공하셨습니다.", user.getUserId());
        return answerService.create(answer);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        if (!isLoginUser(session)) {
            return false;
        }
        Answer answer = answerService.findAnswer(id);
        User loginUser = getSessionUser(session);
        if (!answer.isSameAuthor(loginUser)) {
            return false;
        }
        answerService.delete(id);
        return true;
    }

}

