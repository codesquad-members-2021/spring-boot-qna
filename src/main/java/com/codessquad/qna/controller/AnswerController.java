package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.AnswerRepository;
import com.codessquad.qna.domain.QuestionRepostory;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.NotFoundException;
import com.codessquad.qna.service.AnswerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.utils.SessionUtil.*;

@Controller
public class AnswerController {

    private static final Logger logger = LoggerFactory.getLogger(AnswerController.class);

    private final AnswerService answerService;

    public AnswerController(AnswerService answerservice) {
        this.answerService = answerservice;
    }

    @PostMapping("/qna/{questionId}/answers")
    public String createAnswer(@PathVariable Long questionId, String contents, HttpSession session) {
        answerService.createAnswer(questionId,contents,session);
        return String.format("redirect:/qna/%d", questionId);
    }

    @DeleteMapping("/qna/{questionId}/answers/{answerId}")
    public String removeAnswer(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session) {
        answerService.removeAnswer(questionId,answerId,session);
        return String.format("redirect:/qna/%d", questionId);
    }

}
