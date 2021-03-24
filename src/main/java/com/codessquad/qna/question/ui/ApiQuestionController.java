package com.codessquad.qna.question.ui;

import com.codessquad.qna.question.application.QuestionService;
import com.codessquad.qna.question.dto.QuestionRequest;
import com.codessquad.qna.question.dto.QuestionResponse;
import com.codessquad.qna.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.codessquad.qna.common.HttpSessionUtils.checkAuthorization;
import static com.codessquad.qna.common.HttpSessionUtils.getUserAttribute;

@RestController
@RequestMapping("/api/questions")
public class ApiQuestionController {
    private final QuestionService questionService;

    public ApiQuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<QuestionResponse> create(@Valid QuestionRequest questionRequest, HttpSession session) {
        User writer = getUserAttribute(session);
        QuestionResponse questionResponse = questionService.save(questionRequest, writer);
        return ResponseEntity.created(
                URI.create("/api/questions" + questionResponse.getId())
        ).body(questionResponse);
    }

    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getList(Pageable pageable) {
        return ResponseEntity.ok().body(questionService.getList(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<QuestionResponse> get(@PathVariable Long id, Model model) {
        return ResponseEntity.ok().body(questionService.get(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<QuestionResponse> update(@PathVariable Long id, @Valid QuestionRequest questionRequest, HttpSession session) {
        checkQuestionAuthorization(id, session);
        User writer = getUserAttribute(session);
        return ResponseEntity.ok().body(questionService.update(id, questionRequest, writer));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, HttpSession session) {
        checkQuestionAuthorization(id, session);
        questionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private void checkQuestionAuthorization(Long id, HttpSession session) {
        User writer = questionService.getWriter(id);
        checkAuthorization(writer, session);
    }
}
