package com.codessquad.qna.answer.ui;

import com.codessquad.qna.answer.dto.AnswerRequest;
import com.codessquad.qna.answer.dto.AnswerResponse;
import com.codessquad.qna.question.application.QuestionService;
import com.codessquad.qna.user.domain.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.codessquad.qna.common.HttpSessionUtils.checkAuthorization;
import static com.codessquad.qna.common.HttpSessionUtils.getUserAttribute;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    private final QuestionService questionService;

    public ApiAnswerController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public AnswerResponse createAnswer(@PathVariable Long questionId, @Valid AnswerRequest answerRequest, HttpSession session) {
        User writer = getUserAttribute(session);
        return questionService.addAnswer(questionId, answerRequest, writer);
    }

    @DeleteMapping("{id}")
    public AnswerResponse deleteAnswer(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        checkAnswerAuthorization(id, session);
        return questionService.deleteAnswer(id);
    }

    private void checkAnswerAuthorization(Long id, HttpSession session) {
        User writer = questionService.getAnswerWriter(id);
        checkAuthorization(writer, session);
    }
}
