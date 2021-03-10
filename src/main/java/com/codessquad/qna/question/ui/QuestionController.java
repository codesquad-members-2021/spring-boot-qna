package com.codessquad.qna.question.ui;

import com.codessquad.qna.question.application.QuestionService;
import com.codessquad.qna.question.domain.Question;
import com.codessquad.qna.question.dto.QuestionRequest;
import com.codessquad.qna.question.dto.QuestionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.net.URI;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(@RequestBody QuestionRequest questionRequest) {
        QuestionResponse questionResponse = questionService.saveQuestion(questionRequest);
        return ResponseEntity.created(
                URI.create("/questions/" + questionResponse.getId())
        ).body(questionResponse);
    }

    @PostMapping(value = "create")
    public String createQuestion(Question question) {
        questionService.saveQuestion(QuestionRequest.of(question));
        return "redirect:/questions";
    }

    @GetMapping
    public String getQuestions(Model model) {
        model.addAttribute("questions", questionService.getQuestions());
        return "question/list";
    }

    @GetMapping(value = "{id}")
    public String getQuestion(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.getQuestion(id));
        return "question/show";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
