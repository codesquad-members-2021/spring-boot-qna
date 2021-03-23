package com.codessquad.qna.controller;

import com.codessquad.qna.entity.User;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public String create(@PathVariable long questionId, String contents, HttpSession session) {
        logger.debug("{}번 질문에 답변 생성 요청", questionId);
        User user = HttpSessionUtils.getUser(session);
        answerService.addAnswer(questionId, user, contents);
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("/{answerId}")
    public String delete(@PathVariable long questionId, @PathVariable long answerId, HttpSession session) {
        logger.debug("{}번 질문의 {}번 답변 삭제 요청", questionId, answerId);
        User user = HttpSessionUtils.getUser(session);
        answerService.deleteAnswer(answerId, user);
        return "redirect:/questions/" + questionId;
    }
}
