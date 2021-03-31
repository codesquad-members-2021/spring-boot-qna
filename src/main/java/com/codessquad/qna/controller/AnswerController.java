package com.codessquad.qna.controller;

import com.codessquad.qna.domain.*;
import com.codessquad.qna.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public AnswerController(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }


    @PostMapping
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (! HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new IllegalArgumentException("질문이 존재하지 않습니다."));
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        Answer answer = new Answer(loginUser, question, contents);
        answerRepository.save(answer);
        return String.format("redirect:/questions/%d", questionId);
    }
}
