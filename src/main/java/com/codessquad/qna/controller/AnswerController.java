package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.exception.FailedUserLoginException;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.controller.HttpSessionUtils.getSessionUser;
import static com.codessquad.qna.controller.HttpSessionUtils.isLoginUser;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private final Logger logger = LoggerFactory.getLogger(AnswerController.class);
    private final QuestionService questionService;
    private final AnswerService answerService;

    @Autowired
    public AnswerController(QuestionService questionService, AnswerService answerService) {
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @PostMapping("")
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!isLoginUser(session)) {
            throw new FailedUserLoginException();
        }
        Answer answer = new Answer(getSessionUser(session), questionService.findQuestion(questionId), contents);
        answerService.create(answer);
        logger.info("답변 작성에 성공했습니다.");
        return String.format("redirect:/questions/%d", questionId);
    }
}

