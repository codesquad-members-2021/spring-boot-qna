package com.codessquad.qna.answer.ui;

import com.codessquad.qna.answer.dto.AnswerRequest;
import com.codessquad.qna.answer.dto.AnswerResponse;
import com.codessquad.qna.question.application.QuestionService;
import com.codessquad.qna.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;

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
    public ResponseEntity<AnswerResponse> createAnswer(@PathVariable Long questionId, @Valid AnswerRequest answerRequest, HttpSession session) {
        User writer = getUserAttribute(session);
        AnswerResponse answerResponse = questionService.addAnswer(questionId, answerRequest, writer);
        return ResponseEntity.created(URI.create(
                String.format("/api/questions/%d/answers/%d", questionId, answerResponse.getQuestionId())
        )).body(answerResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long questionId, @PathVariable Long id, HttpSession session) {
        checkAnswerAuthorization(id, session);
        questionService.deleteAnswer(id);
        return ResponseEntity.noContent().build();
    }

    private void checkAnswerAuthorization(Long id, HttpSession session) {
        User writer = questionService.getAnswerWriter(id);
        checkAuthorization(writer, session);
    }
}
