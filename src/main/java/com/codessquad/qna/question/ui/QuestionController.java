package com.codessquad.qna.question.ui;

import com.codessquad.qna.question.application.QuestionService;
import com.codessquad.qna.question.dto.QuestionRequest;
import com.codessquad.qna.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

import static com.codessquad.qna.common.HttpSessionUtils.checkLoggedIn;
import static com.codessquad.qna.common.HttpSessionUtils.getUserAttribute;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("create")
    public String createQuestion(QuestionRequest questionRequest, HttpSession session) {
        User writer = getUserAttribute(session);
        questionService.saveQuestion(questionRequest, writer);
        return "redirect:/questions";
    }

    @GetMapping("form")
    public String getQuestionForm(HttpSession session) {
        checkLoggedIn(session);
        return "question/form";
    }

    @GetMapping
    public String getQuestions(Model model) {
        model.addAttribute("questions", questionService.getQuestions());
        return "question/list";
    }

    @GetMapping("{id}")
    public String getQuestion(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.getQuestion(id));
        return "question/show";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
