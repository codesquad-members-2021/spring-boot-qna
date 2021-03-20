package com.codessquad.qna.question.ui;

import com.codessquad.qna.question.application.QuestionService;
import com.codessquad.qna.question.dto.QuestionRequest;
import com.codessquad.qna.question.dto.QuestionResponse;
import com.codessquad.qna.user.domain.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

import static com.codessquad.qna.common.HttpSessionUtils.checkAuthorization;
import static com.codessquad.qna.common.HttpSessionUtils.getUserAttribute;

@RestController("/api/questions")
public class ApiQuestionController {
    private final QuestionService questionService;

    public ApiQuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public QuestionResponse create(@Valid QuestionRequest questionRequest, HttpSession session) {
        User writer = getUserAttribute(session);
        return questionService.save(questionRequest, writer);
    }

    @GetMapping
    public List<QuestionResponse> getList(Model model) {
        return questionService.getList();
    }

    @GetMapping("{id}")
    public QuestionResponse get(@PathVariable Long id, Model model) {
        return questionService.get(id);
    }

    @PutMapping("{id}")
    public QuestionResponse update(@PathVariable Long id, @Valid QuestionRequest questionRequest, HttpSession session) {
        checkQuestionAuthorization(id, session);
        User writer = getUserAttribute(session);
        return questionService.update(id, questionRequest, writer);
    }

    @DeleteMapping("{id}")
    public QuestionResponse delete(@PathVariable Long id, HttpSession session) {
        checkQuestionAuthorization(id, session);
        return questionService.delete(id);
    }

    private void checkQuestionAuthorization(Long id, HttpSession session) {
        User writer = questionService.getWriter(id);
        checkAuthorization(writer, session);
    }
}
