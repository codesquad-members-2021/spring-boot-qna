package com.codessquad.qna.controller.api;

import com.codessquad.qna.domain.Question;
import com.codessquad.qna.service.QuestionService;
import com.codessquad.qna.util.HttpSessionUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import static com.codessquad.qna.util.HttpSessionUtils.checkSessionUser;
import static com.codessquad.qna.util.HttpSessionUtils.getSessionUser;

@RestController
@RequestMapping("api/questions")
public class ApiQuestionController {
    private final QuestionService questionService;

    public ApiQuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/{questionId}")
    public Question showInDetail(@PathVariable long questionId) {
        return questionService.getOneById(questionId);
    }

    @PostMapping
    public Question create(Question newQuestion, HttpSession session) {
        return questionService.addQuestion(newQuestion, HttpSessionUtils.getUserFromSession(session));
    }

    @PutMapping("/{questionId}")
    public Question update(@PathVariable long questionId, Question newQuestionInfo, HttpSession session) {
        checkSessionUser(session);

        return questionService.update(questionService.getOneById(questionId), newQuestionInfo, getSessionUser(session));
    }

    @DeleteMapping("/{questionId}")
    public Question delete(@PathVariable long questionId, HttpSession session, Model model) {
        checkSessionUser(session);

        return questionService.remove(getSessionUser(session), questionService.getOneById(questionId));
    }
}
