package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.domain.User;
import com.codessquad.qna.exception.IllegalUserAccessException;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.HttpSessionUtils.getUserFromSession;
import static com.codessquad.qna.HttpSessionUtils.isLoginUser;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;

    public AnswerController(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @PostMapping
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!isLoginUser(session)) {
            return "redirect:/users/login";
        }

        User writer = getUserFromSession(session);
        Question question = questionService.findById(questionId);
        answerService.save(writer, question, contents);
        return "redirect:/questions/{questionId}";
    }
    
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        if (!isLoginUser(session)) {
            return "redirect:/users/login";
        }

        User loginUser = getUserFromSession(session);

        System.out.println("hi");

        Answer answer = answerService.findAnswerById(id);
        if (!answer.isAnswerWriter(loginUser)) {
            throw new IllegalUserAccessException();
        }

        answerService.delete(answer);
        return "redirect:/questions/{questionId}";
    }
}
