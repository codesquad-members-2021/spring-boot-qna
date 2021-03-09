package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.repository.AnswerRepository;
import com.codessquad.qna.repository.QuestionRepository;
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

import java.util.Optional;

import static com.codessquad.qna.controller.HttpSessionUtils.getSessionUser;
import static com.codessquad.qna.controller.HttpSessionUtils.isLoginUser;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);
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
            logger.info("로그인 된 사용자가 아닙니다.");
            return "redirect:/users/loginForm";
        }
        User loginUser = getSessionUser(session);
        Question question = questionService.findQuestion(questionId);
        Answer answer = new Answer(loginUser, question, contents);
        answerService.create(answer);
        logger.info("답변 작성에 성공했습니다.");
        return String.format("redirect:/questions/%d", questionId);
    }
}

