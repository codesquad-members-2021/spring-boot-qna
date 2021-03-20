package com.codessquad.qna.controller;

import com.codessquad.qna.domain.question.Question;
import com.codessquad.qna.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/questions")
@RestController
public class ApiQuestionController {

    private final QuestionService questionService;

    public ApiQuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/")
    public Page<Question> pagingList() {
        return questionService.pagingList();
    }
}
