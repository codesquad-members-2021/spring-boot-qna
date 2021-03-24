package com.codessquad.qna.answer.ui;

import com.codessquad.qna.answer.dto.AnswerRequest;
import com.codessquad.qna.question.application.QuestionService;
import com.codessquad.qna.user.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.codessquad.qna.common.HttpSessionUtils.checkAuthorization;
import static com.codessquad.qna.common.HttpSessionUtils.getUserAttribute;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    private final QuestionService questionService;

    public AnswerController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public String create(@PathVariable Long questionId, @Valid AnswerRequest answerRequest, HttpSession session) {
        User writer = getUserAttribute(session);
        questionService.addAnswer(questionId, answerRequest, writer);
        return "redirect:/questions/" + questionId;
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        checkAnswerAuthorization(id, session);
        questionService.deleteAnswer(id);
        return "redirect:/questions/" + questionId;
    }

    private void checkAnswerAuthorization(Long id, HttpSession session) {
        User writer = questionService.getAnswerWriter(id);
        checkAuthorization(writer, session);
    }
}
