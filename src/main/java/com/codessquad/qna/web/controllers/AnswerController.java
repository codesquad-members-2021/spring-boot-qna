package com.codessquad.qna.web.controllers;

import com.codessquad.qna.web.domain.Answer;
import com.codessquad.qna.web.domain.Question;
import com.codessquad.qna.web.domain.User;
import com.codessquad.qna.web.service.AnswerService;
import com.codessquad.qna.web.service.QuestionService;
import com.codessquad.qna.web.utility.SessionUtility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;

    private AnswerController(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @PostMapping
    public String addAnswer(@PathVariable Long questionId, HttpSession session, String contents) {
        Question question = questionService.findById(questionId);
        User writer = SessionUtility.findSessionedUser(session);
        answerService.save(new Answer(question, writer, contents)); //TODO 서비스에 추출하기
        return "redirect:/questions/{questionId}";
    }

    @DeleteMapping("/{id}")
    public String deleteAnswer(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        User writer = SessionUtility.findSessionedUser(session);
        answerService.delete(id, writer);
        return "redirect:/questions/{questionId}";
    }
}
